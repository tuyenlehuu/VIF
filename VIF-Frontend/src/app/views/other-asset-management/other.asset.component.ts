import { Component, OnInit } from '@angular/core';
import { AssetService } from '../../services/asset.service';
import { Asset } from '../../models/Asset.model';
import { first } from 'rxjs/operators';

@Component({
  selector: 'app-other.asset',
  templateUrl: './other.asset.component.html',
  styleUrls: ['./other.asset.component.scss']
})
export class OtherAssetComponent implements OnInit {
  otherAssetList: Asset[] =[];
  constructor(private assetService: AssetService) {
    this.assetService.getOtherAssetNotShares().pipe(first()).subscribe((res: any) =>{
      this.otherAssetList = res.data;
      console.log("this.otherAssetList",this.otherAssetList );
    });
  }

  ngOnInit() {

  }

  confirmDel(template, assetId){

  }

}
