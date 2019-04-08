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
import { BuySellAsset } from '../../models/BuySellAsset.model';
import { InvestManagementService } from '../../services/invest.management.service';
import { AppParam } from '../../models/AppParam.model';
import { DevidendObject } from '../../models/Devidend.model';

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
    amountAssetAvaiable: number
    dType: number;
    dRate: number;

    constructor(private toastrService: ToastrService,
        private assetService: AssetService, private investManagementService: InvestManagementService,
        private fb: FormBuilder) { }

    createDevidendForm() {
        this.devidendForm = this.fb.group({
            dAssetSelectedId: [this.dAssetSelectedId, Validators.required],
            amountAssetAvaiable: [this.amountAssetAvaiable],
            dType: [this.dType, Validators.required],
            dRate: [this.dRate, Validators.required],
        }, {
                validator: [NotEqualZero('dRate')]
        });
    }

    resetForm() {
        this.refreshData();
        this.createDevidendForm();
    }

    saveDevidendTransaction() {
        if (this.devidendForm.invalid) {
            return;
        }
        this.devidendDistribution();
    }

    devidendDistribution() {
        let devidendobj : DevidendObject = new DevidendObject();
        devidendobj.assetId = this.devidendForm.value.dAssetSelectedId;
        devidendobj.amount = this.devidendForm.value.amountAssetAvaiable;
        devidendobj.rate = this.devidendForm.value.dRate;
        devidendobj.type = this.devidendForm.value.dType;

        console.log(devidendobj);
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

    refreshData() {
        this.assetService.getAllShares().pipe(first()).subscribe((respons: any) => {
            this.assets = respons.data;
            if (this.assets) {
                this.dAssetSelectedId = this.assets[0].id;
                this.amountAssetAvaiable = this.assets[0].amount
                this.dType = 1;
                this.dRate = 0;
                this.createDevidendForm();
            }
        });
    }

    onChangeAsset() {
        this.dAssetSelectedId = this.devidendForm.value.dAssetSelectedId;
        this.assets.forEach(asset => {
            if (asset.id === this.dAssetSelectedId) {
                this.amountAssetAvaiable = asset.amount;
                this.gDevidendForm.amountAssetAvaiable.setValue(this.amountAssetAvaiable);
            }
        });
    }

    ngOnInit(): void {
        this.assetService.getAllShares().pipe(first()).subscribe((respons: any) => {
            this.assets = respons.data;
            if (this.assets) {
                this.dAssetSelectedId = this.assets[0].id;
                this.amountAssetAvaiable = this.assets[0].amount
                this.dType = 1;
                this.dRate = 0;
                this.createDevidendForm();
            }
        });
    }
}
