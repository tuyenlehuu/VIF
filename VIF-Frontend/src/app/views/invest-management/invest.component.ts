import { Component, Inject, OnInit } from '@angular/core';
import { DOCUMENT } from '@angular/common';
import { getStyle, rgbToHex } from '@coreui/coreui/dist/js/coreui-utilities';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NotEqualZero } from '../../helpers/function.share';
import { ToastrService } from 'ngx-toastr';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { ResponseObject } from '../../models/Response.model';
import { error } from '@angular/compiler/src/util';
import { AssetService } from '../../services/asset.service';
import { Asset } from '../../models/Asset.model';
import { first, catchError } from 'rxjs/operators';
import { BuySellCCQ } from '../../models/BuySelCCQ.model';
import { config } from '../../config/application.config';
import { BuySellAsset } from '../../models/BuySellAsset.model.';
import { InvestManagementService } from '../../services/invest.management.service.';

@Component({
  templateUrl: 'invest.component.html',
  styleUrls: ['invest.component.scss']
})
export class InvestComponent implements OnInit {
  isBuyScreen: boolean = true;
  buyForm: FormGroup;
  sellForm: FormGroup;
  submitted = false;

  assets: Asset[] = [];
  assetSelectedId: number;
  responseObject: ResponseObject;

  constructor(private modalService: BsModalService, private toastrService: ToastrService, 
    private assetService: AssetService, private investManagementService: InvestManagementService, 
    private fb: FormBuilder) {}

createBuyForm() {
  this.buyForm = this.fb.group({
    bAssetSelectedId: [this.assetSelectedId, Validators.required],
    bAmountAsset: [{value: 0, disabled: false}, Validators.required],
    bMoney: [{value:0, disabled: true}, Validators.required],
    bPrice: [0, Validators.required]
},{
    validator: [NotEqualZero('bAmountAsset'), NotEqualZero('bMoney'), NotEqualZero('bPrice')]
});
}

createSellForm() {
  this.sellForm = this.fb.group({
    sAssetSelectedId: [this.assetSelectedId, Validators.required],
    sAmountAsset: [0, Validators.required],
    sMoney: [{value: 0, disabled: true}, Validators.required],
    sPrice: [0, Validators.required]
},{
    validator: [NotEqualZero('sAmountAsset'), NotEqualZero('sMoney'), NotEqualZero('sPrice')]
});
}

resetForm() {
  if (this.isBuyScreen) {
      this.createBuyForm();
  } else {
      this.createSellForm();
  }
}

saveInvestTransaction() {
  if (this.isBuyScreen) {
      this.submitted = true;
      // stop here if form is invalid
      if (this.buyForm.invalid) {
          return;
      }
      this.buySecurities();
  } else {
      this.submitted = true;
      // stop here if form is invalid
      if (this.sellForm.invalid) {
          return;
      }
      this.sellSecurities();
  }
}

buySecurities(){
    let buyAssetObject: BuySellAsset = new BuySellAsset();
    buyAssetObject.assetId = this.buyForm.value.bAssetSelectedId;
    buyAssetObject.amount = this.buyForm.value.bAmountAsset;
    buyAssetObject.price = this.buyForm.value.bPrice;

    this.investManagementService.buyAsset(buyAssetObject).pipe(first()).subscribe((respons: any) => {
        this.responseObject = respons;
        console.log( this.responseObject);
        if (this.responseObject.code === 200) {
            this.showSuccess("Mua chứng khoán thành công!");
            this.resetForm();
        } else {
            this.showError("Không mua được chứng khoán! Vui lòng liên hệ quản trị viên");
        }
    });
}

sellSecurities(){
    let sellAssetObject: BuySellAsset = new BuySellAsset();
    sellAssetObject.assetId = this.buyForm.value.sAssetSelectedId;
    sellAssetObject.amount = this.buyForm.value.sAmountAsset;
    sellAssetObject.price = this.buyForm.value.sPrice;

    this.investManagementService.sellAsset(sellAssetObject).pipe(first()).subscribe((respons: any) => {
        this.responseObject = respons;
        console.log( this.responseObject);
        if (this.responseObject.code === 200) {
            this.showSuccess("Bán chứng khoán thành công!");
            this.resetForm();
        } else {
            this.showError("Không bán được chứng khoán. Vui lòng liên hệ quản trị viên!");
        }
    });
}

onKeyBPrice(event: any){
    if (this.buyForm.invalid) {
        return;
    }
    var mAmount = this.buyForm.value.bAmountAsset;
    var currentPrice = event.target.value;
    currentPrice = currentPrice.toString().replace(',','');
    this.buyAssetForm.bMoney.setValue(mAmount*currentPrice);
}

onKeySPrice(event: any){
    if (this.sellForm.invalid) {
        return;
    }
    var bAmountAsset = this.sellForm.value.sAmountAsset;
    var currentPrice = event.target.value;
    currentPrice = currentPrice.toString().replace(',','');
    // console.log("currentPrice", currentPrice);
    this.sellAssetForm.sMoney.setValue(bAmountAsset*currentPrice);
}

get buyAssetForm() { return this.buyForm.controls; }

get sellAssetForm() { return this.sellForm.controls; }

 showSuccess(mes: string) {
        this.toastrService.success('', mes, {
            timeOut: config.timeoutToast
        });
    }

    showError(mes: string) {
        this.toastrService.error('', mes, {
            timeOut: config.timeoutToast
        });
    }

    changeScreen(typeScreen: number) {
        if (typeScreen === 1) {
            this.isBuyScreen = true;
        } else {
            this.isBuyScreen = false;
            this.createSellForm();
        }
    }

ngOnInit(): void {
    this.assetService.getAll().pipe(first()).subscribe((respons: any) => {
        // console.log("data: ", respons);
        this.assets = respons.data;
        console.log(  this.assets);
        if (this.assets) {
            this.assetSelectedId = this.assets[0].id;
            // console.log("data: ", this.customerSelectedId);
            // this.amountCCQAvaiable = this.customers[0].totalCcq;
            this.createBuyForm();
        }
    });
}

}
