import { Component, OnInit, TemplateRef } from '@angular/core';
import { AssetService } from '../../services/asset.service';
import { Asset } from '../../models/Asset.model';
import { first } from 'rxjs/operators';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';

@Component({
  selector: 'app-other.asset',
  templateUrl: './asset.component.html',
  styleUrls: ['./asset.component.scss']
})
export class AssetComponent implements OnInit {
  assetList: Asset[] = [];
  modalRef: BsModalRef;

  constructor(private assetService: AssetService, private modalService: BsModalService) {
    this.assetService.getAll().pipe(first()).subscribe((res: any) => {
      this.assetList = res.data;
    });
  }

  ngOnInit() {

  }

  confirmDel(template: TemplateRef<any>, userId: string) {
    this.modalRef = this.modalService.show(template);
    this.modalRef.content = userId;
  }

  deleteAsset(){
    
  }

}
