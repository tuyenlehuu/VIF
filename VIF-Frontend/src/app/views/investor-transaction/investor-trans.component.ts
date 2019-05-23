import { Component, OnInit, TemplateRef } from '@angular/core';
import { BuySellCCQ } from '../../models/BuySelCCQ.model';
import { UserService } from '../../services/user.service';
import { first, catchError } from 'rxjs/operators';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { error } from '@angular/compiler/src/util';
import { ToastrService } from 'ngx-toastr';
import { config } from '../../config/application.config';
import { ResponseObject } from '../../models/Response.model';
import { CustomerService } from '../../services/customer.service';
import { Customer } from '../../models/Customer.model';
import { InvestorTransService } from '../../services/investor.transaction.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NotEqualZero, ValidateSellAmount } from '../../helpers/function.share';
import { AssetService } from '../../services/asset.service';
import { Asset } from '../../models/Asset.model';

@Component({
    templateUrl: 'investor-trans.component.html',
    styleUrls: ['investor-trans.component.scss']
})
export class InvestorTransComponent implements OnInit {
    isBuyScreen: boolean = true;
    customers: Customer[] = [];
    customerSelectedId: number;
    responseObject: ResponseObject;
    buyForm: FormGroup;
    sellForm: FormGroup;
    submitted = false;
    amountCCQAvaiable: number;
    assetSelectedCode: string;
    isCCQDB = false;
    isSellCCQDB = false;
    transType = [
        {
            name: 'Giao dịch CCQ',
            value: '1'
        },
        {
            name: 'Giao dịch CCQ đảm bảo',
            value: '2'
        }
    ];
    assets: Asset[] = [];

    constructor(private modalService: BsModalService, private toastrService: ToastrService,
        private customerService: CustomerService, private investorTransService: InvestorTransService,
        private fb: FormBuilder, private assetService:AssetService) {
    }

    createBuyForm() {
        this.isCCQDB = false;
        this.buyForm = this.fb.group({
            bCustomerSelectedId: [this.customerSelectedId, Validators.required],
            bAmountCCQ: [{ value: 0, disabled: true }, Validators.required],
            bMoney: [0, Validators.required],
            bPrice: [0, Validators.required],
            bTransType: ['1', Validators.required],
            bCCQDBSelectedCode: [this.assetSelectedCode]
        }, {
                validator: [NotEqualZero('bAmountCCQ'), NotEqualZero('bMoney'), NotEqualZero('bPrice')]
        });
    }

    createSellForm() {
        this.sellForm = this.fb.group({
            sCustomerSelectedId: [this.customerSelectedId, Validators.required],
            sAmountCCQ: [0, Validators.required],
            sMoney: [{ value: 0, disabled: true }, Validators.required],
            sPrice: [0, Validators.required],
            sAmountCCQAvai: [this.amountCCQAvaiable],
            sTransType: ['1', Validators.required],
            sCCQDBSelectedCode: [this.assetSelectedCode]
        }, {
                validator: [ValidateSellAmount('sAmountCCQ', 'sAmountCCQAvai'), NotEqualZero('sMoney'), NotEqualZero('sPrice')]
            });
    }

    ngOnInit(): void {
        this.customerService.getAll().pipe(first()).subscribe((respons: any) => {
            // console.log("data: ", respons);
            this.customers = respons;
            if (this.customers) {
                this.customerSelectedId = this.customers[0].id;
                // console.log("data: ", this.customerSelectedId);
                this.amountCCQAvaiable = this.customers[0].totalCcq;
                this.createBuyForm();
            }
        });

        this.assetService.getAssetByGroupId(3).pipe(first()).subscribe((respons: any) => {
            // console.log("data: ", respons);
            this.assets = respons.data;
            if(this.assets){
                this.assetSelectedCode = this.assets[0].assetCode;
            }
        });
    }

    get buyCCQForm() { return this.buyForm.controls; }

    get sellCCQForm() { return this.sellForm.controls; }

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
        this.resetForm();
        if (typeScreen === 1) {
            this.isBuyScreen = true;
        } else {
            this.isBuyScreen = false;
            this.createSellForm();
        }
    }

    buyCCQ() {
        let buyCCQObject: BuySellCCQ = new BuySellCCQ();
        buyCCQObject.customerId = this.buyForm.value.bCustomerSelectedId;
        buyCCQObject.money = this.buyForm.value.bMoney;
        buyCCQObject.priceCCQ = this.buyForm.value.bPrice;

        this.investorTransService.buyCCQ(buyCCQObject).pipe(first()).subscribe((respons: any) => {
            this.responseObject = respons;
            if (this.responseObject.code === 200) {
                this.showSuccess("Đầu tư thành công!");
                this.resetForm();
                this.amountCCQAvaiable = Number((this.amountCCQAvaiable + Number(buyCCQObject.money/buyCCQObject.priceCCQ)).toFixed(2));
            } else {
                this.showError("Đầu tư thất bại. Vui lòng liên hệ quản trị viên!");
            }
        });
    }

    sellCCQ() {
        let sellCCQObject: BuySellCCQ = new BuySellCCQ();
        sellCCQObject.customerId = this.sellForm.value.sCustomerSelectedId;
        sellCCQObject.amountCCQ = this.sellForm.value.sAmountCCQ;
        sellCCQObject.priceCCQ = this.sellForm.value.sPrice;

        this.investorTransService.sellCCQ(sellCCQObject).pipe(first()).subscribe((respons: any) => {
            this.responseObject = respons;
            if (this.responseObject.code === 200) {
                this.showSuccess("Rút vốn thành công!");
                this.resetForm();
                this.amountCCQAvaiable = Number((this.amountCCQAvaiable - sellCCQObject.amountCCQ).toFixed(2));
            } else {
                this.showError("Rút vốn thất bại. Vui lòng liên hệ quản trị viên!");
            }
        });
    }

    saveCCQ() {
        if (this.isBuyScreen) {
            this.submitted = true;
            // stop here if form is invalid
            if (this.buyForm.invalid) {
                return;
            }
            if(this.buyForm.value.bTransType=='1'){
                this.buyCCQ();
            }else{
                // console.log("Mua trai phieu!");
                this.buyEnsureCCQ();
            }
        } else {
            this.submitted = true;
            // stop here if form is invalid
            if (this.sellForm.invalid) {
                return;
            }
            if(this.sellForm.value.bTransType=='1'){
                this.sellCCQ();
            }else{
                console.log("Ban trai phieu!");
                // this.sell
            }
            
        }
    }

    buyEnsureCCQ() {
        let buyEnsureCCQObject: BuySellCCQ = new BuySellCCQ();
        buyEnsureCCQObject.customerId = this.buyForm.value.bCustomerSelectedId;
        buyEnsureCCQObject.money = this.buyForm.value.bMoney;
        buyEnsureCCQObject.priceCCQ = this.buyForm.value.bPrice;
        buyEnsureCCQObject.ensureCCQCode = this.buyForm.value.bCCQDBSelectedCode;

        this.investorTransService.buyEnsureCCQ(buyEnsureCCQObject).pipe(first()).subscribe((respons: any) => {
            this.responseObject = respons;
            if (this.responseObject.code === 200) {
                this.showSuccess("Đầu tư thành công!");
                this.resetForm(); 
                this.amountCCQAvaiable = Number((this.amountCCQAvaiable + Number(buyEnsureCCQObject.money/buyEnsureCCQObject.priceCCQ)).toFixed(2));
            } else {
                this.showError("Đầu tư thất bại. Vui lòng liên hệ quản trị viên!");
            }
        });
    }

    resetForm() {
        if (this.isBuyScreen) {
            this.createBuyForm();
        } else {
            this.createSellForm();
        }
        this.isCCQDB = false;
        this.isSellCCQDB = false;
        this.onChangeCustomer();
    }

    onKeyBPrice(event: any) {
        if (this.buyForm.invalid) {
            return;
        }
        var mMoney = this.buyForm.value.bMoney;
        var currentPrice = this.buyForm.value.bPrice;
        this.buyCCQForm.bAmountCCQ.setValue(mMoney / currentPrice);
    }


    onKeySPrice(event: any) {
        if (this.sellForm.invalid) {
            return;
        }
        var mAmountCCQ = this.sellForm.value.sAmountCCQ;
        var currentPrice = this.sellForm.value.sPrice;
        // currentPrice = currentPrice.toString().replace(',', '');
        this.sellCCQForm.sMoney.setValue(mAmountCCQ * currentPrice);
    }

    onChangeCustomer() {
        if (this.isBuyScreen) {
            this.customerSelectedId = this.buyForm.value.bCustomerSelectedId;
            this.amountCCQAvaiable = 0;
            this.customerService.getById(this.customerSelectedId).subscribe((res:any) =>{
                var mCus: Customer = res;
                this.amountCCQAvaiable = mCus.totalCcq;
            });
        } else {
            this.customerSelectedId = this.sellForm.value.sCustomerSelectedId;
            this.amountCCQAvaiable = 0;
            this.customerService.getById(this.customerSelectedId).subscribe((res:any) =>{
                var mCus: Customer = res;
                this.amountCCQAvaiable = mCus.totalCcq;
                this.sellCCQForm.sAmountCCQAvai.setValue(mCus.totalCcq);
            });
        }
    }

    onChangeTransType(){
        if(this.buyForm.value.bTransType == '2'){
            this.isCCQDB = true;
            this.onChangeEnsureCCQ();
        }else{
            this.isCCQDB = false;
        }
    }

    onChangeSellTransType(){
        // console.log("sTransType: ", this.sellForm.value.sTransType);
        if(this.sellForm.value.sTransType == '2'){
            this.isSellCCQDB = true;
            this.onChangeEnsureCCQ();
        }else{
            this.isSellCCQDB = false;
        }
    }

    onChangeEnsureCCQ(){
        if(this.isBuyScreen){
            this.investorTransService.getEnsureCCQByCusAsset(this.buyForm.value.bCustomerSelectedId, this.buyForm.value.bCCQDBSelectedCode).pipe(first()).subscribe((respons: any) => {
                if(respons.data){
                    this.amountCCQAvaiable = respons.data;
                }else{
                    this.amountCCQAvaiable = 0.00;
                }
            });
        }else{
            this.investorTransService.getEnsureCCQByCusAsset(this.sellForm.value.sCustomerSelectedId, this.sellForm.value.sCCQDBSelectedCode).pipe(first()).subscribe((respons: any) => {
                if(respons.data){
                    this.amountCCQAvaiable = respons.data;
                }else{
                    this.amountCCQAvaiable = 0.00;
                }
            });
        }
        
    }

}
