import { Component, OnInit } from '@angular/core';
import { Customer } from '../../models/Customer.model';
import { CustomerService } from '../../services/customer.service';
import { Router, ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { config } from '../../config/application.config';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DatePipe, formatDate } from '@angular/common';
import { isDate } from 'util';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import {BsDatepickerConfig, BsDatepickerDirective, BsLocaleService} from 'ngx-bootstrap';
import {defineLocale} from 'ngx-bootstrap/chronos';



@Component({
    templateUrl: 'customer-management.create.edit.component.html'
})
export class CECustomerComponent implements OnInit {
    customer: Customer;
    bsConfig: Partial<BsDatepickerConfig>;
    sub: any;
    id: any;
    addCustomerForm: FormGroup;
    submitted = false;
    editCustomerForm: FormGroup;
    dateOfBirth : Date;
    colorTheme = 'theme-blue';



    status = [
        {
            name: 'Hoạt động',
            value: 1
        },
        {
            name: 'Ngừng hoạt động',
            value: 0
        }
    ];

    constructor( private route: ActivatedRoute, private customerService: CustomerService, private router: Router, private toastrService: ToastrService, private fb: FormBuilder) {
        
        this.bsConfig = Object.assign({}, { containerClass: this.colorTheme });
        
        this.createForm();
    }

    ngOnInit(): void {
        this.id = this.route.snapshot.params['id'];
        if (this.id > 0) {
            this.customerService.getById(this.id).subscribe((res: Customer) => {
                this.customer = res;
               
                this.createEditForm();
            })
        } else {
            this.customer = new Customer();
        }
    }

    createForm() {
        this.addCustomerForm = this.fb.group({
            fullName: ['', Validators.required],
            identityNumber: ['', Validators.required],
            email: ['', Validators.required],
            dateOfBirth: [ new Date() , Validators.required],
            avatar: ['', Validators.required],
            status: [1, Validators.required],
            identityDocFront: ['', Validators.required],
            identityDocBack: [new Date(), Validators.required] ,
            signContractDate: [new Date(),Validators.required]
        });
    }

    createEditForm(){
        this.editCustomerForm = this.fb.group({
            eFullName: [this.customer.fullName, Validators.required],
            eEmail: [this.customer.email, Validators.required],
            eDateOfBirth : [this.customer.dateOfBirth, Validators.required],
            eStatus: [this.customer.activeFlg, Validators.required]
        });
    }

    saveCustomer(customer: Customer) {
        if (this.id > 0) {
          
            this.customerService.update(customer).subscribe(res => {
          
                this.showSuccess('Cập nhật thành công');
                this.router.navigate(['/customer-management']);
            }, (err) => {
                this.showError('Cập nhật customer không thành công!');
                console.log(err);
            });
        } else {
            this.customerService.addCustomer(customer).subscribe(res => {
                this.showSuccess('Thêm mới thành công');
                this.router.navigate(['/customer-management']);
            }, (err) => {
                this.showError('Thêm mới customer không thành công!');
                console.log(err);
            });
        }
    }

    get addForm() { return this.addCustomerForm.controls; }

    get editForm() { return this.editCustomerForm.controls; }

    onAddSubmit() {
        this.submitted = true;
        // stop here if form is invalid
        if (this.addCustomerForm.invalid) {
            return;
        }
        this.customer.fullName = this.addCustomerForm.value.fullName;
        this.customer.email= this.addCustomerForm.value.email;
        this.customer.identityNumber = this.addCustomerForm.value.identityNumber;
        this.customer.avatar= this.addCustomerForm.value.avatar;
        this.customer.dateOfBirth = this.addCustomerForm.value.dateOfBirth;
        this.customer.activeFlg = this.addCustomerForm.value.activeFlg;
        this.customer.identityDocBack=this.addCustomerForm.value.identityDocBack;
        this.customer.identityDocFront=this.addCustomerForm.value.identityDocFront;
        this.customer.signContractDate=this.addCustomerForm.value.signContractDate;
        this.saveCustomer(this.customer);
    }

    onEditSubmit(){
        if (this.editCustomerForm.invalid) {
            return;
        }
        
        this.customer.fullName = this.editCustomerForm.value.fullName;
        this.customer.email = this.editCustomerForm.value.email;
        this.customer.dateOfBirth = this.editCustomerForm.value.dateOfBirth;
        this.customer.activeFlg = this.editCustomerForm.value.activeFlg;
        this.saveCustomer(this.customer);
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
}
