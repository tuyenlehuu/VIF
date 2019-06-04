import { Component, OnInit } from '@angular/core';
import { Customer } from '../../models/Customer.model';
import { CustomerService } from '../../services/customer.service';
import { Router, ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { config } from '../../config/application.config';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { BsDatepickerConfig } from 'ngx-bootstrap';
//import { Observable } from 'rxjs';
import { forkJoin } from 'rxjs';
import { BillingInfo } from '../../models/BillingInfo.model';
import { BillingInfoService } from '../../services/billing.service';
import { first } from 'rxjs/operators';

@Component({
  templateUrl: 'billing-info.create.edit.component.html'
})
export class CEBillingInfoComponent implements OnInit {
  billingInfo: BillingInfo;
  sub: any;
  id: any;
  addBInfoForm: FormGroup;
  submitted = false;
  editBInfoForm: FormGroup;
  showUpdateDate: string;
  customers:Customer[] = [];
  customerSelectedId: number;

  constructor(private route: ActivatedRoute, private bInfoService: BillingInfoService, private customerService: CustomerService,
    private router: Router, private toastrService: ToastrService, private fb: FormBuilder) {
 
    this.customerService.getAll().pipe(first()).subscribe((respons: any) => {
      // console.log("data: ", respons);
      this.customers = respons;
      if (this.customers) {
          this.customerSelectedId = this.customers[0].id;
          this.createForm();
      }
  });

  }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    console.log("id:", this.id);
    if (this.id > 0) {
      this.bInfoService.getById(this.id).subscribe((res: any) => {
        this.billingInfo = res;
        this.createEditForm();
      })
    } else {
      this.billingInfo = new BillingInfo();
    }
  }

  createForm() {
    this.addBInfoForm = this.fb.group({
      bankName: ['', Validators.required],
      bankBranch: ['', Validators.required],
      updateDate: [new Date(), Validators.required],
      bankAccount: ['', Validators.required],
      accountName: ['', Validators.required],
      customerControl: [this.customerSelectedId, Validators.required]
    });

  }

  setTextDate(dateToString: string): void {
    this.showUpdateDate = dateToString;
  }


  createEditForm() {
    this.editBInfoForm = this.fb.group({
      eBankName: [{ value: this.billingInfo.bankName, disabled: (this.billingInfo.activeFlg == 1) ? false : true }, Validators.required],
      eBankBranch: [{ value: this.billingInfo.bankBranch, disabled: (this.billingInfo.activeFlg == 1) ? false : true }, Validators.required],
      eUpdateDate: [new Date(), Validators.required],
      eBankAccount: [{ value: this.billingInfo.bankAccount, disabled: (this.billingInfo.activeFlg == 1) ? false : true }, Validators.required],
      eAccountName: [{ value: this.billingInfo.accountName, disabled: (this.billingInfo.activeFlg == 1) ? false : true }, Validators.required]
    });
    var uDate: Date = new Date(this.billingInfo.updateDate);
    this.setTextDate(uDate.toDateString());


  }


  get addForm() { return this.addBInfoForm.controls; }

  get editForm() { return this.editBInfoForm.controls; }

  saveBInfo(bInfo: BillingInfo) {
    if (this.id > 0) {
      this.bInfoService.update(bInfo).subscribe(res => {
        this.showSuccess('Cập nhật thành công');
        this.router.navigate(['/billing-info']);
      }, (err) => {
        this.showError('Cập nhật không thành công!');
        // console.log(err);
      });
    } else {
      this.bInfoService.add(bInfo).subscribe(res => {
        this.showSuccess('Thêm mới thành công');
        this.router.navigate(['/billing-info']);
      }, (err) => {
        this.showError('Thêm mới không thành công!');
        // console.log(err);
      });
    }
  }


  onAddSubmit() {
    this.submitted = true;
    if (this.addBInfoForm.invalid) {
      this.showError('Chưa hoàn thành thông tin!');
      return;
    }
    else {

      this.billingInfo.accountName = this.addBInfoForm.value.accountName;
      this.billingInfo.bankAccount = this.addBInfoForm.value.bankAccount;
      this.billingInfo.bankBranch = this.addBInfoForm.value.bankBranch;
      this.billingInfo.bankName = this.addBInfoForm.value.bankName;
      this.billingInfo.updateDate = this.addBInfoForm.value.updateDate;
      let cus: Customer = new Customer();
      cus.id = this.addBInfoForm.value.customerControl;
      this.billingInfo.customer = cus;

      console.log("binfooo==", this.billingInfo);
      this.saveBInfo(this.billingInfo);
    }

  }

  onChangeCustomer() {
  }



  onEditSubmit() {
    if (this.editBInfoForm.invalid) {
      return;
    }
    this.billingInfo.accountName = this.editBInfoForm.value.eAccountName;
    this.billingInfo.bankAccount = this.editBInfoForm.value.eBankAccount;
    this.billingInfo.bankBranch = this.editBInfoForm.value.eBankBranch;
    this.billingInfo.bankName = this.editBInfoForm.value.eBankName;
    this.billingInfo.updateDate = this.editBInfoForm.value.eUpdateDate;
    this.saveBInfo(this.billingInfo);
  }

  //file upload event  
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

}