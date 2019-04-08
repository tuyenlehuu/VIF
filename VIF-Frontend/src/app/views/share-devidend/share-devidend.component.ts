import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NotEqualZero, ValidateSellAmount } from '../../helpers/function.share';
import { ToastrService } from 'ngx-toastr';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { ResponseObject } from '../../models/Response.model';
import { AssetService } from '../../services/asset.service';
import { Asset } from '../../models/Asset.model';
import { first } from 'rxjs/operators';
import { config } from '../../config/application.config';
import { BuySellAsset } from '../../models/BuySellAsset.model.';
import { InvestManagementService } from '../../services/invest.management.service.';

@Component({
    templateUrl: 'share-devidend.component.html',
    styleUrls: ['share-devidend.component.scss']
})
export class ShareDevidendComponent implements OnInit {
  
    devidendForm: FormGroup;
    submitted = false;
    assets: Asset[] = [];
    dAssetSelectedId: number;
    responseObject: ResponseObject;
    amountAssetAvaiable: number;

    constructor(private toastrService: ToastrService,
        private assetService: AssetService, private investManagementService: InvestManagementService,
        private fb: FormBuilder) { }

    createDevidendForm() {
        this.devidendForm = this.fb.group({
            dAssetSelectedId: [this.dAssetSelectedId, Validators.required],
            amountAssetAvaiable: [0],
            dType: [0, Validators.required],
            dRate: [0, Validators.required],
        }, {
                validator: []
            });
    }

    resetForm() {
            this.createDevidendForm();
    }

    saveDevidendTransaction() {
        if (this.devidendForm.invalid) {
            return;
        }
        this.cashDeviden();
        
    }

    cashDeviden(){
        
    }

    shareDeviden() {
    }

 
    onKeyBPrice(event: any) {
        if (this.devidendForm.invalid) {
            return;
        }
      
    }

    onKeyBAmount(event: any) {
        if (this.devidendForm.invalid) {
            return;
        }
    }

    onKeySPrice(event: any) {
     
    }

    get gDevidendForm() { return this.devidendForm.controls; }

  
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

    refreshData(){
    }

    onChangeAsset() {
    }

    ngOnInit(): void {
        this.assetService.getAllShares().pipe(first()).subscribe((respons: any) => {
            this.assets = respons.data;
            if (this.assets) {
                this.dAssetSelectedId = this.assets[0].id;
                this.amountAssetAvaiable = this.assets[0].amount
            }
        });
        console.log(this.assets);
        this.createDevidendForm();
    }

}
