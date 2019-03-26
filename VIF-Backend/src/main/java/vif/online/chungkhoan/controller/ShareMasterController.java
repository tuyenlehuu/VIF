package vif.online.chungkhoan.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;

import vif.online.chungkhoan.entities.ShareMaster;
import vif.online.chungkhoan.services.ShareMasterService;

@Controller
@RequestMapping("cophieu")
@CrossOrigin(origins= {"*"})
public class ShareMasterController {

	@Autowired
	private ShareMasterService cophieuService;

	@Autowired
	private Environment env;

	@GetMapping("/code/{code}")
	public ResponseEntity<ShareMaster> getCophieuByCode(@PathVariable("code") String code) {
		ShareMaster cp = cophieuService.getCophieuByCode(code);
		return new ResponseEntity<ShareMaster>(cp, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ShareMaster> getCophieuById(@PathVariable("id") Integer id) {
		ShareMaster cp = cophieuService.getCophieuById(id);
		return new ResponseEntity<ShareMaster>(cp, HttpStatus.OK);
	}

	@GetMapping("getAlls")
	public ResponseEntity<List<ShareMaster>> getAllCophieu() {
		List<ShareMaster> list = cophieuService.getAllCophieu();
		return new ResponseEntity<List<ShareMaster>>(list, HttpStatus.OK);
	}

	@PostMapping("add")
	public ResponseEntity<Void> addArticle(@RequestBody ShareMaster cp, UriComponentsBuilder builder) {
		boolean flag = cophieuService.addCophieu(cp);
		if (flag == false) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/cophieu/{code}").buildAndExpand(cp.getCpCode()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@PutMapping("update")
	public ResponseEntity<ShareMaster> updateUser(@RequestBody ShareMaster cp) {
		cophieuService.updateCophieu(cp);
		return new ResponseEntity<ShareMaster>(cp, HttpStatus.OK);
	}

	@DeleteMapping("delete/{code}")
	public ResponseEntity<Void> deleteCophieu(@PathVariable("code") String cpCode) {
		cophieuService.deleteCophieu(cpCode);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@GetMapping("syncData")
	public ResponseEntity<Void> syncData() {
		try {
			String urlOrigin = env.getProperty("urlOrigin");
			syncDataCp(cophieuService, urlOrigin);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<Void>(headers, HttpStatus.OK);
	}

	public void syncDataCp(ShareMasterService cpService, String urlOrigin) throws IOException {
		Document doc = Jsoup.connect(urlOrigin).maxBodySize(0).timeout(20000).get();
		Elements elements = doc.select("tbody#sortable tr");
		// elements.remove(0);
		List<Element> lstElements = elements.subList(2, elements.size());
		Map<String, ShareMaster> mapCophieu = new HashMap<>();
		for (Element e : lstElements) {
			ShareMaster cophieu = new ShareMaster();
			Element td = e.select("td").get(0);
			Elements aLst = td.getElementsByTag("a");
			if (aLst != null && aLst.size() > 0) {
				Element a = aLst.get(0);
				if (a != null) {
					String myId = a.html().toLowerCase() + "_close";
					String companyName = a.attr("title");
					Element price = e.getElementById(myId);
					if (price != null) {
						Element pr = price.select("td span").get(0);
						cophieu.setCpCode(a.html());
						cophieu.setCpName(companyName);
						if (pr.html().isEmpty()) {
							cophieu.setCpPrice("0");
						} else {
							cophieu.setCpPrice(pr.html());
						}
						cophieu.setLastUpdate(new Date());
						mapCophieu.put(cophieu.getCpCode(), cophieu);
					}
				}
			}
		}
		
		List<ShareMaster> listFromDB = cophieuService.getAllCophieu();
		if(listFromDB == null || listFromDB.size() <= 0) {
			for(Map.Entry<String, ShareMaster> entry : mapCophieu.entrySet()) {
				cpService.addCophieu(entry.getValue());
			}
		}else {
			for (ShareMaster mCpDB : listFromDB) {
				String currentPrice = mapCophieu.get(mCpDB.getCpCode()).getCpPrice();
				if(currentPrice.equals(mCpDB.getCpPrice())) {
					continue;
				}else {
					DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					System.out.println(mCpDB.getCpCode() + " Old Price: " + mCpDB.getCpPrice() + "; New Price: " + currentPrice + "; Time update: " + dateFormat.format(new Date()));
					mCpDB.setCpPrice(currentPrice);
					mCpDB.setLastUpdate(new Date());
					cpService.updateCophieu(mCpDB);
				}
			}
		}
	}
}
