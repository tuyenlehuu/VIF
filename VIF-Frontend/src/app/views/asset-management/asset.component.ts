import { Component, OnInit, TemplateRef } from '@angular/core';
import { AssetService } from '../../services/asset.service';
import { Asset } from '../../models/Asset.model';
import { first } from 'rxjs/operators';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { ToastrService } from 'ngx-toastr';
import { config } from '../../config/application.config';
import { Pager } from '../../models/Pager';
import { GroupAsset } from '../../models/GroupAsset.model';

@Component({
  selector: 'app-other.asset',
  templateUrl: './asset.component.html',
  styleUrls: ['./asset.component.scss']
})
export class AssetComponent implements OnInit {
  assetList: Asset[] = [];
  modalRef: BsModalRef;
  assetSearch: Asset = new Asset();
  p: number = 1;
  total: number;
  pageSize: number = 5;
  groupAssets: GroupAsset[];

  constructor(private assetService: AssetService, private modalService: BsModalService, private toastrService: ToastrService) {
    this.assetService.getGroupAsset().pipe(first()).subscribe((res: any) => {
      this.groupAssets = res.data;
      console.log("this.groupAssets",this.groupAssets);
    });
  }

  ngOnInit() {
    this.getPage(1);
  }

  getPage(page: number) {
    var pager: Pager = new Pager();
    pager.page = page;
    pager.pageSize = this.pageSize;
    let mGroup: GroupAsset = new GroupAsset();
    if(!this.assetSearch.groupAsset.id){
      mGroup.id=null;
      this.assetSearch.groupAsset = mGroup;
    }
    
    
    this.assetService.getAssetsByCondition(this.assetSearch, pager).pipe(first()).subscribe((respons: any) => {
      this.assetList = respons.data;
      this.total = respons.totalRow;
      this.p = page;
    });
  }

  confirmDel(template: TemplateRef<any>, userId: string) {
    this.modalRef = this.modalService.show(template);
    this.modalRef.content = userId;
  }

  deleteAsset() {
    this.assetService.deleteAssetById(this.modalRef.content).subscribe(res => {
      this.showSuccess('Xóa thành công');
      this.getPage(1);
    }, catchError => {
      console.log("result: ", catchError);
    });
    this.modalRef.hide();
  }

  showSuccess(mes: string) {
    this.toastrService.success('', mes, {
      timeOut: config.timeoutToast
    });
  }

  search(){
    this.getPage(1);
  }

}
