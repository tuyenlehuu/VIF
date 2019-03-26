import { Component, Inject, OnInit } from '@angular/core';
import { DOCUMENT } from '@angular/common';
import { getStyle, rgbToHex } from '@coreui/coreui/dist/js/coreui-utilities';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NotEqualZero } from '../../helpers/function.share';
import { ToastrService } from 'ngx-toastr';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { ResponseObject } from '../../models/Response.model';
import { error } from '@angular/compiler/src/util';
import { InvestorTransService } from '../../services/investor.transaction.service';
import { CustomerService } from '../../services/customer.service';
import { Customer } from '../../models/Customer.model';
import { first, catchError } from 'rxjs/operators';
import { BuySellCCQ } from '../../models/BuySelCCQ.model';
import { config } from '../../config/application.config';

@Component({
  templateUrl: 'invest.component.html',
  styleUrls: ['invest.component.scss']
})
export class InvestComponent implements OnInit {
  isBuyScreen: boolean = true;
  buyForm: FormGroup;
  sellForm: FormGroup;
  submitted = false;

  customers: Customer[] = [];
  customerSelectedId: number;
  responseObject: ResponseObject;

  constructor(private modalService: BsModalService, private toastrService: ToastrService, 
    private customerService: CustomerService, private investorTransService: InvestorTransService, 
    private fb: FormBuilder) {}

createBuyForm() {
  this.buyForm = this.fb.group({
    bCustomerSelectedId: [this.customerSelectedId, Validators.required],
    bAmountCCQ: [{value: 0, disabled: true}, Validators.required],
    bMoney: [0, Validators.required],
    bPrice: [0, Validators.required]
},{
    validator: [NotEqualZero('bAmountCCQ'), NotEqualZero('bMoney'), NotEqualZero('bPrice')]
});

console.log(this.buyForm);
}

createSellForm() {
  this.sellForm = this.fb.group({
    sCustomerSelectedId: [this.customerSelectedId, Validators.required],
    sAmountCCQ: [0, Validators.required],
    sMoney: [{value: 0, disabled: true}, Validators.required],
    sPrice: [0, Validators.required]
},{
    validator: [NotEqualZero('sAmountCCQ'), NotEqualZero('sMoney'), NotEqualZero('sPrice')]
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

}

sellSecurities(){
  
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
      } else {
          this.showError("Đầu tư thất bại. Vui lòng liên hệ quản trị viên!");
      }
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

    changeScreen(typeScreen: number) {
        if (typeScreen === 1) {
            this.isBuyScreen = true;
        } else {
            this.isBuyScreen = false;
            this.createSellForm();
        }
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
ngOnInit(): void {
  this.customerService.getAll().pipe(first()).subscribe((respons: any) => {
    // console.log("data: ", respons);
    this.customers = respons;
    console.log(this.customers);
    if (this.customers) {
        this.customerSelectedId = this.customers[0].id;
        // console.log("data: ", this.customerSelectedId);
        this.createBuyForm();
    }
});
}

}
