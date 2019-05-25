import { Component, OnInit, TemplateRef } from '@angular/core';
import { first, catchError } from 'rxjs/operators';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { ToastrService } from 'ngx-toastr';
import { BuySellCCQ } from '../../models/BuySelCCQ.model';
import { config } from '../../config/application.config';
import { ResponseObject } from '../../models/Response.model';
import { Customer } from '../../models/Customer.model';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Pager } from '../../models/Pager';
import { InvestAppro } from '../../models/InvestAppro.model'
import { InvestApproService } from '../../services/investAppro.service';
import { formatDate } from '../../helpers/function.share';
import { CustomerService } from '../../services/customer.service';
import { BsDatepickerConfig } from 'ngx-bootstrap';
import { ActivatedRoute, Router } from '@angular/router';
import { InvestorTransService } from '../../services/investor.transaction.service';

@Component({
  templateUrl: 'action-investAppro.component.html',

})
export class ActionInvestApproComponent implements OnInit {
  updateInvestForm: FormGroup;
  investAppro: InvestAppro;
  customer: Customer;
  id: number;
  amountCCQAvaiable: number;
  submitted = false;
  status = [
    {
      name: 'Pending',
      value: 0
    },
    {
      name: 'approval',
      value: 1
    },
    {
      name: 'reject',
      value: 2
    }
  ];
  responseObject: ResponseObject;
  customerSelectedId: number;
  customerService: CustomerService;
  constructor(private route: ActivatedRoute, private appParamService: InvestApproService, private router: Router, private toastrService: ToastrService, private fb: FormBuilder, private investorTransService: InvestorTransService, ) {
    this.createForm();
  }
  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
  }
  createForm() {
    this.updateInvestForm = this.fb.group({
      totalCCQ: [{ value: this.investAppro.customer.totalCcq, disabled: true }, Validators.required],
      amountCCQ: [{ value: this.investAppro.amount, disabled: true }, Validators.required],
      status: [{ value: this.investAppro.status, disabled: true }, Validators.required]
    });
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

  buyCCQ() {
    let buyCCQObject: BuySellCCQ = new BuySellCCQ();
    buyCCQObject.customerId = this.updateInvestForm.value.bCustomerSelectedId;
    buyCCQObject.money = this.updateInvestForm.value.bMoney;
    buyCCQObject.priceCCQ = this.updateInvestForm.value.bPrice;

    this.investorTransService.buyCCQ(buyCCQObject).pipe(first()).subscribe((respons: any) => {
      this.responseObject = respons;
      if (this.responseObject.code === 200) {
        this.showSuccess("Thành công!");
        this.resetForm();
        this.amountCCQAvaiable = Number((this.amountCCQAvaiable + Number(buyCCQObject.money / buyCCQObject.priceCCQ)).toFixed(2));
      } else {
        this.showError("thất bại!");
      }
    });
  }

  sellCCQ() {
    let sellCCQObject: BuySellCCQ = new BuySellCCQ();
    sellCCQObject.customerId = this.updateInvestForm.value.sCustomerSelectedId;
    sellCCQObject.amountCCQ = this.updateInvestForm.value.sAmountCCQ;
    sellCCQObject.priceCCQ = this.updateInvestForm.value.sPrice;

    this.investorTransService.sellCCQ(sellCCQObject).pipe(first()).subscribe((respons: any) => {
      this.responseObject = respons;
      if (this.responseObject.code === 200) {
        this.showSuccess("Thành công!");
        this.resetForm();
        this.amountCCQAvaiable = Number((this.amountCCQAvaiable - sellCCQObject.amountCCQ).toFixed(2));
      } else {
        this.showError("Thất bại!");
      }
    });
  }

  saveCCQ() {
    if (this.investAppro.typeOfRequest == 1) {
      this.submitted = true;
      // stop here if form is invalid
      if (this.updateInvestForm.invalid) {
        return;
      }
      this.buyCCQ();
    } else {
      this.submitted = true;
      // stop here if form is invalid
      if (this.updateInvestForm.invalid) {
        return;
      }
      this.sellCCQ();
    }
  }
  resetForm() {
    this.createForm();
  }

  onChangeCustomer() {
    if (this.buyCCQ) {
      this.customerSelectedId = this.updateInvestForm.value.bCustomerSelectedId;
      this.amountCCQAvaiable = 0;
      this.customerService.getById(this.customerSelectedId).subscribe((res: any) => {
        var mCus: Customer = res;
        this.amountCCQAvaiable = mCus.totalCcq;
      });
    } else {
      this.customerSelectedId = this.updateInvestForm.value.sCustomerSelectedId;
      this.amountCCQAvaiable = 0;
      this.customerService.getById(this.customerSelectedId).subscribe((res: any) => {
        var mCus: Customer = res;
        this.amountCCQAvaiable = mCus.totalCcq;
      });
    }
  }

}