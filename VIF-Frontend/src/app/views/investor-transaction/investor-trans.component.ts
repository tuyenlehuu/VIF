import { Component, OnInit, TemplateRef } from '@angular/core';
import { User } from '../../models/User.model';
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

    constructor(private modalService: BsModalService, private toastrService: ToastrService, 
        private customerService: CustomerService, private investorTransService: InvestorTransService, 
        private fb: FormBuilder) {
            this.customerService.getAll().pipe(first()).subscribe((respons: any) => {
                this.customers = respons;
                if (this.customers) {
                    this.customerSelectedId = this.customers[0].id;
                    console.log("data: ", this.customerSelectedId);
                    this.createBuyForm();
                    // this.createSellForm();
                }
            });
        
    }

    createBuyForm() {
        this.buyForm = this.fb.group({
            bCustomerSelectedId: [this.customerSelectedId, Validators.required],
            bAmountCCQ: [0, Validators.required],
            bMoney: [0, Validators.required],
            bPrice: [0, Validators.required]
        });
    }

    createSellForm() {
        this.sellForm = this.fb.group({
            sCustomerSelectedId: [this.customerSelectedId, Validators.required],
            sAmountCCQ: [0, Validators.required],
            sMoney: [0, Validators.required],
            sPrice: [0, Validators.required]
        });
    }

    ngOnInit(): void {
        this.customerService.getAll().pipe(first()).subscribe((respons: any) => {
            // console.log("data: ", respons);
            this.customers = respons;
            if (this.customers) {
                this.customerSelectedId = this.customers[0].id;
            }
        });
        // this.search();
    }

    get buyCCQForm() { return this.buyForm.controls; }

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
        this.investorTransService.buyCCQ(this.buyForm.value.bCustomerSelectedId, this.buyForm.value.bMoney, this.buyForm.value.bPrice).pipe(first()).subscribe((respons: any) => {
            this.responseObject = respons;
            if (this.responseObject.code === 200) {
                this.showSuccess("Đầu tư thành công!");
            } else {
                this.showError("Đầu tư thất bại. Vui lòng liên hệ quản trị viên!");
            }
        });
    }

    sellCCQ() {
        this.investorTransService.sellCCQ(this.customerSelectedId, 500, 10000).pipe(first()).subscribe((respons: any) => {
            this.responseObject = respons;
            if (this.responseObject.code === 200) {
                this.showSuccess("Rút vốn thành công!");
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

    cancelProcess() {
        if (this.isBuyScreen) {
            this.createBuyForm();
        } else {
            this.createSellForm();
        }
    }
}
