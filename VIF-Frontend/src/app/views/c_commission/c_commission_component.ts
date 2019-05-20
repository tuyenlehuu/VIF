import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NotEqualZero, ValidateSellAmount } from '../../helpers/function.share';
import { ToastrService } from 'ngx-toastr';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { ResponseObject } from '../../models/Response.model';
import { first } from 'rxjs/operators';
import { config } from '../../config/application.config';
import { CustomerService } from '../../services/customer.service';
import { Customer } from '../../models/Customer.model';
import { CommissionObj } from '../../models/CommissionObj.model';
import { InvestorTransService } from '../../services/investor.transaction.service';

@Component({
    templateUrl: 'c_commission_component.html',
    styleUrls: ['c_commission_component.scss']
})
export class CompanyCommissionComponent implements OnInit {
    customers: Customer[] = [];
    cComForm: FormGroup;
    bCustomerSelectedId: Number;
    cType: string;
    cAmount: Number;
    isCashCommission: boolean = true;
    responseObject: ResponseObject;
    constructor(private investorTransService: InvestorTransService, private customerService: CustomerService, private fb: FormBuilder, private toastrService: ToastrService) {
    }
    createCommissionDevideForm() {
        this.cComForm = this.fb.group({
            cType: [{ value: "CASH", disabled: false }, Validators.required],
            cAmount: [{ value: 0, disabled: false }, Validators.required],
            bCustomerSelectedId: [{ value: this.bCustomerSelectedId, disabled: true }, Validators.required],
        }, {
                validator: [NotEqualZero('cAmount')]
            });
    }
    resetForm() {
        this.createCommissionDevideForm();
    }

    comCommissionDivide() {
        if (this.cComForm.invalid) {
            return;
        }
        let commissionObj = new CommissionObj();
        commissionObj.amount = this.cComForm.value.cAmount;
        commissionObj.cType = this.cComForm.value.cType;
        commissionObj.customerID = this.bCustomerSelectedId;
        this.investorTransService.cCommissionDivide(commissionObj).pipe(first()).subscribe((respons: any) => {
            this.responseObject = respons;
            if (this.responseObject.code === 200) {
                this.showSuccess("Chia lợi tức thành công!");
                this.resetForm();
            } else {
                this.showError("Chia lợi tức thất bại. Vui lòng liên hệ quản trị viên!");
            }
        });
    }

    onChangeTypeCommission() {
        var cType = this.cComForm.value.cType;
        if (cType == "CASH") {
            this.isCashCommission = true;
        } else {
            this.isCashCommission = false;
        }
    }

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

    get cComDivideForm() { return this.cComForm.controls; }

    ngOnInit(): void {
        this.customerService.getAll().pipe(first()).subscribe((respons: any) => {
            this.customers = respons;
            if (this.customers) {
                for (let i = 0; i < this.customers.length; i++) {
                    if (this.customers[i].code == "VIFADMIN") {
                        this.bCustomerSelectedId = this.customers[i].id;
                    }
                }
                this.createCommissionDevideForm();
            }
        });
    }
}
