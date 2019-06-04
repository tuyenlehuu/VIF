import { Component, OnInit, TemplateRef } from '@angular/core';
import { first, catchError } from 'rxjs/operators';
import { ToastrService } from 'ngx-toastr';
import { config } from '../../config/application.config';
import { Customer } from '../../models/Customer.model';
import { User } from '../../models/User.model';
import { UserService } from '../../services/user.service';
import { Pager } from '../../models/Pager';
import { InvestRequest } from '../../models/InvestRequest.model'
import { InvestRequestService } from '../../services/invest.request.service';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { ValidateSellAmount, NotEqualZero } from '../../helpers/function.share';
import { Asset } from '../../models/Asset.model';
import { AssetService } from '../../services/asset.service';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';


@Component({
    templateUrl: 'invest-req.component.html',
    styleUrls: ['invest-req.component.scss']

})
export class InvestRequestComponent implements OnInit {
    isBuyScreen: boolean = true;
    users: User[] = [];
    user: User = new User();
    customer: Customer;
    submitted = false;
    amountCCQAvaiable: number = null;
    buyForm: FormGroup;
    sellForm: FormGroup;
    investRequest: InvestRequest = new InvestRequest();
    date = new Date();
    price: number = null;
    priceCCQTemp: number = null;
    isCCQDB = false;
    isSellCCQDB = false;
    assetSelectedCode: string;
    CQQDBtemp: number;
    modalRef: BsModalRef;
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
    assetTransCCQ: Asset;
    constructor(private toastrService: ToastrService, private userService: UserService,private modalService: BsModalService,
        private requestService: InvestRequestService, private fb: FormBuilder,private assetService:AssetService) {
    }


    createBuyForm() {
        this.buyForm = this.fb.group({
            bCCQ: [{ value: 0, disabled: true }, Validators.required],
            bMoney: [0, Validators.required],
            price: [this.priceCCQTemp, Validators.required],
            bTransType: ['1', Validators.required],
            bCCQDBSelectedCode: [this.assetSelectedCode]
        }, {
                validator: [NotEqualZero('bCCQ'), NotEqualZero('bMoney')]
            });
    }


    createSellForm() {
        this.sellForm = this.fb.group({
            sCCQ: [0, Validators.required],
            sMoney: [{ value: 0, disabled: true }, Validators.required],
            sAmountCCQAvai: [this.amountCCQAvaiable],
            price: [this.priceCCQTemp, Validators.required],
            sTransType: ['1', Validators.required],
            sCCQDBSelectedCode: [this.assetSelectedCode]
        }, {
                validator: [ValidateSellAmount('sCCQ', 'sAmountCCQAvai'), NotEqualZero('sMoney')]
            });
    }



    ngOnInit(): void {

        this.isBuyScreen = true;
        var pager: Pager = new Pager();
        var x: string;
        var currentUser = localStorage.getItem("currentUser");
        var u = JSON.parse(currentUser);
        x = u.username.toString();
        this.user.username = x;
        pager.page = 1;

        this.userService.getUsersByCondition(this.user, pager).pipe(first()).subscribe((res: any) => {
            this.users = res.data;
            this.user = this.users[0];
            this.customer = this.user.customer;
            this.amountCCQAvaiable = this.customer.totalCcq;
            this.createBuyForm();
        });

        this.requestService.getPriceCCQ().pipe(first()).subscribe((res: any) => {
            this.price = res;
           // console.log("priceeeee",this.priceCCQTemp);
            this.priceCCQTemp = this.price;
        })

        this.assetService.getAssetByGroupId(3).pipe(first()).subscribe((respons: any) => {
            this.assets = respons.data;
            if(this.assets){
                this.assetSelectedCode = this.assets[0].assetCode;
            }
        });

        this.assetService.getAssetByGroupId(5).pipe(first()).subscribe((respons: any) => {
            this.assetTransCCQ = respons.data[0];
            this.investRequest.asset = this.assetTransCCQ;
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
        
        if (typeScreen === 1) {
            this.isBuyScreen = true;
        } else {
            this.isBuyScreen = false;
        }
        this.resetForm();
        
    }

    buyCCQ() {
        this.investRequest.amount = this.buyForm.value.bCCQ;
        this.investRequest.typeOfRequest = 1;
        this.investRequest.customer = this.customer;
        this.investRequest.money = this.buyForm.value.bMoney;
        this.investRequest.createDate = this.date;
        this.investRequest.typeOfInvest = this.buyForm.value.bTransType; 
        this.investRequest.price = this.buyForm.value.price;
        this.requestService.add(this.investRequest).subscribe((res: any) => {
            if (res != null) {
                this.showSuccess("Gửi yêu cầu thành công");
                this.resetForm();
            }
            () => {
                this.showError("Gửi yêu cầu thất bại");
            }
        });
        
    }

    sellCCQ() {
        this.investRequest.money = this.sellForm.value.sMoney;
        this.investRequest.typeOfRequest = 2;
        this.investRequest.customer = this.customer;
        this.investRequest.amount = this.sellForm.value.sCCQ;
        this.investRequest.createDate = this.date;
        this.investRequest.typeOfInvest = this.sellForm.value.sTransType;
        this.investRequest.price = this.sellForm.value.price;
        this.requestService.add(this.investRequest).subscribe((res: any) => {

            if (res != null) {
                this.showSuccess("Gửi yêu cầu thành công");
                this.resetForm();
            }
            () => {
                this.showError("Gửi yêu cầu thất bại");
            }
        });

    }

    saveCCQ() {
       
            if (this.isBuyScreen) {
                this.submitted = true;
                if (this.buyForm.invalid) {
                    return;
                }
                this.buyCCQ();
            } else {
                this.submitted = true;
                if (this.sellForm.invalid) {
                    return;
                }
                this.sellCCQ();
            }
            this.modalRef.hide();
       
       

    }

    resetForm() {
        this.investRequest.asset = this.assetTransCCQ;
        this.amountCCQAvaiable = this.customer.totalCcq;
        this.isCCQDB = false;
        this.isSellCCQDB = false;
        this.price = this.priceCCQTemp;
        if (this.isBuyScreen) {
            this.createBuyForm();
        } else {
            this.createSellForm();
        }
        this.modalRef.hide();

       
    
    }
    onKeyMoney(event: any) {
        this.buyCCQForm.bCCQ.setValue(this.buyForm.value.bMoney / this.price);
        this.buyForm.value.bCCQ = Number((this.buyForm.value.bMoney / this.price).toFixed(2));
        
    }

    onKeyCCQ(event: any) {
        this.sellCCQForm.sMoney.setValue(Number((this.sellForm.value.sCCQ * this.price).toFixed(2)));
       
       if(this.isSellCCQDB==false) {
           this.amountCCQAvaiable = Number((this.customer.totalCcq - this.sellForm.value.sCCQ).toFixed(2));
       }else {
        this.amountCCQAvaiable = Number((this.sellForm.value.sAmountCCQAvai - this.sellForm.value.sCCQ).toFixed(2));
       }
        this.sellForm.value.sMoney = Number((this.sellForm.value.sCCQ * this.price ).toFixed(2));

    }

    onChangeTransType(){
        if(this.buyForm.value.bTransType == '2'){
            this.isCCQDB = true;
            this.onChangeEnsureCCQ();
        }else{
            this.investRequest.asset = this.assetTransCCQ;
            this.isCCQDB = false;
            this.amountCCQAvaiable=this.customer.totalCcq;
            this.resetPrice();
            
        }
    }


    resetPrice(){
        this.price = this.priceCCQTemp;
        if(this.isBuyScreen){ 
            this.buyCCQForm.price.setValue(this.price);
        }else{
            this.sellCCQForm.price.setValue(this.price);
        }
    }

    onChangeSellTransType(){
        if(this.sellForm.value.sTransType == '2'){
            this.isSellCCQDB = true;
            this.onChangeEnsureCCQ();
        }else{
            this.investRequest.asset = this.assetTransCCQ;
            this.isSellCCQDB = false;
            this.amountCCQAvaiable=this.customer.totalCcq;
        }
    }

    onChangeEnsureCCQ(){
        console.log("SELECT CODE=>>",this.buyForm.value.bCCQDBSelectedCode);
        if(this.isBuyScreen){
            this.assetService.getByCode(this.buyForm.value.bCCQDBSelectedCode).subscribe((res:any)=>{
                this.investRequest.asset = res.data;
                this.price = this.investRequest.asset.currentPrice;
                this.buyCCQForm.price.setValue(this.price);
            })
            this.requestService.getEnsureCCQByCusAsset(this.customer.id, this.buyForm.value.bCCQDBSelectedCode).pipe(first()).subscribe((respons: any) => {
                if(respons.data){
                    this.amountCCQAvaiable = respons.data;
                      
                }else{
                    this.amountCCQAvaiable = 0.00;
                }
            });
        }else{
            this.assetService.getByCode(this.sellForm.value.sCCQDBSelectedCode).subscribe((res:any)=>{
                this.investRequest.asset = res.data;
                this.price = this.investRequest.asset.currentPrice;
                this.sellCCQForm.price.setValue(this.price);

            })
            this.requestService.getEnsureCCQByCusAsset(this.customer.id, this.sellForm.value.sCCQDBSelectedCode).pipe(first()).subscribe((respons: any) => {
                if(respons.data){
                    this.amountCCQAvaiable = respons.data;
                    this.sellCCQForm.sAmountCCQAvai.setValue(this.amountCCQAvaiable);
                    //this.sellCCQForm.sAmountCCQAvai.setValue(this.amountCCQAvaiable);
                }else{
                    this.amountCCQAvaiable = 0.00;
                }
            });
        }
        
    }


    confirm(template: TemplateRef<any>) {
        if(this.isBuyScreen){
            if(this.buyForm.value.bMoney==0){
                this.showError("Số tiền không thể bằng không !")
            }else{
                this.modalRef = this.modalService.show(template);
            }

        }else{
            if(this.sellForm.value.sCCQ==0){
                this.showError("Số lượng CCQ không thể bằng không !");
            }else if(this.amountCCQAvaiable<0){
                this.showError("Số lượng CCQ quá giới hạn !");
            }else{
                this.modalRef = this.modalService.show(template);
            }
        }
       
       
    }


}
