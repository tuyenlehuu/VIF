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
import { InvestManagementService } from '../../services/invest.management.service';
import { DividendObject } from '../../models/Dividend.model';

@Component({
    templateUrl: 'share-dividend.component.html',
    styleUrls: ['share-dividend.component.scss']
})
export class ShareDividendComponent implements OnInit {
    dividendForm: FormGroup;
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

    createdividendForm() {
        this.dividendForm = this.fb.group({
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
        this.createdividendForm();
    }

    saveDevidendTransaction() {
        if (this.dividendForm.invalid) {
            return;
        }
        this.devidendDistribution();
    }

    devidendDistribution() {
        let dividendobj: DividendObject = new DividendObject();
        dividendobj.assetId = this.dividendForm.value.dAssetSelectedId;
        dividendobj.amount = this.dividendForm.value.amountAssetAvaiable;
        dividendobj.rate = this.dividendForm.value.dRate;
        dividendobj.type = this.dividendForm.value.dType;

        this.investManagementService.dividendTrans(dividendobj).pipe(first()).subscribe((respons: any) => {
            this.responseObject = respons;
            if (this.responseObject.code === 200) {
                this.showSuccess("Chia cổ tức thành công!");
                this.resetForm();
            } else {
                this.showError("Không chia được cổ tức! Vui lòng liên hệ quản trị viên");
            }
        });
        this.assetService.getAllShares();
        console.log(dividendobj);
    }

    get gdividendForm() { return this.dividendForm.controls; }


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
                this.createdividendForm();
            }
        });
    }

    onChangeAsset() {
        this.dAssetSelectedId = this.dividendForm.value.dAssetSelectedId;
        this.assets.forEach(asset => {
            if (asset.id === this.dAssetSelectedId) {
                this.amountAssetAvaiable = asset.amount;
                this.gdividendForm.amountAssetAvaiable.setValue(this.amountAssetAvaiable);
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
                this.createdividendForm();
            }
        });
    }
}
