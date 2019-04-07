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

    constructor(private modalService: BsModalService, private toastrService: ToastrService,
        private customerService: CustomerService, private investorTransService: InvestorTransService,
        private fb: FormBuilder) {
    }

    createBuyForm() {
        this.buyForm = this.fb.group({
            bCustomerSelectedId: [this.customerSelectedId, Validators.required],
            bAmountCCQ: [{ value: 0, disabled: true }, Validators.required],
            bMoney: [0, Validators.required],
            bPrice: [0, Validators.required]
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
            sAmountCCQAvai: [this.amountCCQAvaiable]
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
                this.amountCCQAvaiable = this.amountCCQAvaiable + Number((buyCCQObject.money/buyCCQObject.priceCCQ).toFixed(2));
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
                this.amountCCQAvaiable = this.amountCCQAvaiable - sellCCQObject.amountCCQ;
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
            this.buyCCQ();
        } else {
            this.submitted = true;
            // stop here if form is invalid
            if (this.sellForm.invalid) {
                return;
            }
            this.sellCCQ();
        }
    }

    resetForm() {
        if (this.isBuyScreen) {
            this.createBuyForm();
        } else {
            this.createSellForm();
        }
    }

    onKeyBPrice(event: any) {
        if (this.buyForm.invalid) {
            return;
        }
        var mMoney = this.buyForm.value.bMoney;
        var currentPrice = event.target.value;
        currentPrice = currentPrice.toString().replace(',', '');
        // console.log("currentPrice", currentPrice);
        this.buyCCQForm.bAmountCCQ.setValue(mMoney / currentPrice);
    }

    onKeySPrice(event: any) {
        if (this.sellForm.invalid) {
            return;
        }
        var mAmountCCQ = this.sellForm.value.sAmountCCQ;
        var currentPrice = event.target.value;
        currentPrice = currentPrice.toString().replace(',', '');
        // console.log("currentPrice", currentPrice);
        this.sellCCQForm.sMoney.setValue(mAmountCCQ * currentPrice);
    }

    onChangeCustomer() {
        if (this.isBuyScreen) {
            this.customerSelectedId = this.buyForm.value.bCustomerSelectedId;
            this.amountCCQAvaiable = 0;
            this.customers.forEach(customer => {
                if (customer.id === this.customerSelectedId) {
                    this.amountCCQAvaiable = customer.totalCcq;
                }
            });
        } else {
            this.customerSelectedId = this.sellForm.value.sCustomerSelectedId;
            this.amountCCQAvaiable = 0;
            this.customers.forEach(customer => {
                if (customer.id === this.customerSelectedId) {
                    this.amountCCQAvaiable = customer.totalCcq;
                    this.sellCCQForm.sAmountCCQAvai.setValue(customer.totalCcq);
                }
            });
        }
    }

}
