import { Component, OnInit } from '@angular/core';
import { Customer } from '../../models/Customer.model';
import { CustomerService } from '../../services/customer.service';
import { Router, ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { config } from '../../config/application.config';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { BsDatepickerConfig} from 'ngx-bootstrap';
//import { Observable } from 'rxjs';
import { forkJoin } from 'rxjs';
//import { from } from 'rxjs';
//import { concatAll } from 'rxjs/operators';



@Component({
    templateUrl: 'customer-management.create.edit.component.html',
    styleUrls: ['./customer-management.c.e.css']
})
export class CECustomerComponent implements OnInit {
    customer: Customer;
    //customer$: Observable<Customer>;
    bsConfig: Partial<BsDatepickerConfig>;
    sub: any;
    id: any;
    addCustomerForm: FormGroup;
    submitted = false;
    editCustomerForm: FormGroup;
    //uploadForm: FormGroup; 
    showDateOfBirth: string;
    showSignContractDate: string;
    colorTheme = 'theme-blue';
    localUrlBack: any[];
    localUrlFront: any[];
    selectFileAvatar: File;

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

    constructor(private route: ActivatedRoute, private customerService: CustomerService, private router: Router, private toastrService: ToastrService, private fb: FormBuilder) {
        this.bsConfig = Object.assign({}, { containerClass: this.colorTheme });
        this.createForm();


    }

    ngOnInit(): void {
        this.id = this.route.snapshot.params['id'];
        console.log("id:", this.id);
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
            fileFront: [''],
            fileBack: [''],
            fullName: ['', Validators.required],
            identityNumber: ['', Validators.required],
            email: ['', Validators.required],
            dateOfBirth: [new Date(), Validators.required],
            status: [{value:1,disabled: true}, Validators.required],
            identityDocFront: ['', Validators.required],
            identityDocBack: ['',Validators.required],
            signContractDate: [new Date(), Validators.required]
            
        });

    }

    setTextBirthOfDate(dateToString: string): void {
        this.showDateOfBirth = dateToString;
    }

    setTextSignContractDate(dateToString: string): void {
        this.showSignContractDate = dateToString;
    }

    createEditForm() {
        this.editCustomerForm = this.fb.group({
            eFullName: [{ value: this.customer.fullName, disabled: (this.customer.activeFlg == 1) ? false : true }, Validators.required],
            eEmail: [{ value: this.customer.email, disabled: (this.customer.activeFlg == 1) ? false : true }, Validators.required],
            eDateOfBirth: [{ value: this.customer.dateOfBirth, disabled: (this.customer.activeFlg == 1) ? false : true }, Validators.required],
            eSignContractDate: [{ value: this.customer.signContractDate, disabled: (this.customer.activeFlg == 1) ? false : true }, Validators.required],
            eIdentityNumber: [{ value: this.customer.identityNumber, disabled: (this.customer.activeFlg == 1) ? false : true }, Validators.required]
        });

        var dateBirth: Date = new Date(this.customer.dateOfBirth);
        var dateSign: Date = new Date(this.customer.signContractDate);
        this.setTextBirthOfDate(dateBirth.toDateString());
        this.setTextSignContractDate(dateSign.toDateString());

    }

    saveCustomer(customer: Customer) {
        if (this.id > 0) {
            this.customerService.update(customer).subscribe(res => {
                this.showSuccess('Cập nhật thành công');
                this.router.navigate(['/customer-management']);
            }, (err) => {
                this.showError('Cập nhật customer không thành công!');
                // console.log(err);
            });
        } else {
            this.customerService.addCustomer(customer).subscribe(res => {
                this.showSuccess('Thêm mới thành công');
                this.router.navigate(['/customer-management']);
            }, (err) => {
                this.showError('Thêm mới customer không thành công!');
                // console.log(err);
            });
        }
    }

    get addForm() { return this.addCustomerForm.controls; }

    get editForm() { return this.editCustomerForm.controls; }

    onAddSubmit() {
        this.submitted = true;


        if (this.addCustomerForm.invalid) {
            this.showError('Chưa hoàn thành thông tin!');
            return;
        }

        else {
            this.customer.fullName = this.addCustomerForm.value.fullName;
            this.customer.email = this.addCustomerForm.value.email;
            this.customer.identityNumber = this.addCustomerForm.value.identityNumber;
            this.customer.dateOfBirth = this.addCustomerForm.value.dateOfBirth;
            this.customer.activeFlg = this.addCustomerForm.value.activeFlg;
            this.customer.signContractDate = this.addCustomerForm.value.signContractDate;
            this.loadImage();


        }

    }

    loadImage() {
   
        let uploadDataBack = new FormData();
        let uploadDataFront = new FormData();
        uploadDataBack.set('file', this.addCustomerForm.get('fileBack').value);
        uploadDataFront.set('file', this.addCustomerForm.get('fileFront').value);
        //const fileGroup = of(ava, front, back);
        forkJoin(
            this.customerService.upFileFront(uploadDataFront),
            this.customerService.upFileBack(uploadDataBack)

        )
            .subscribe(([res1, res2]) => {
                this.customer.identityDocFront = res1;
                this.customer.identityDocBack = res2;
                this.checkCompleteElement();
            });


    }


    checkCompleteElement() {
        if (this.customer.identityDocBack != null && this.customer.identityDocFront != null) {
            this.saveCustomer(this.customer);
            return;
        } else {
            
            if (this.customer.identityDocBack == null) { this.showError('Chưa upload ảnh mặt sau CMT!'); }
            if (this.customer.identityDocFront == null) { this.showError('Chưa upload ảnh mặt trước CMT!'); }
        }


    }


    onEditSubmit() {
        if (this.editCustomerForm.invalid) {
            return;
        }
        this.customer.fullName = this.editCustomerForm.value.eFullName;
        this.customer.email = this.editCustomerForm.value.eEmail;
        this.customer.dateOfBirth = this.editCustomerForm.value.eDateOfBirth;
        this.customer.identityNumber = this.editCustomerForm.value.eIdentityNumber;
        this.customer.signContractDate = this.editCustomerForm.value.eSignContractDate;
        console.log('cus.BirthDate: ' + this.editCustomerForm.value.eDateOfBirth);
        this.saveCustomer(this.customer);
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


  

    showPreviewBack(event: any) {
        if (event.target.files && event.target.files[0]) {
            var reader = new FileReader();
            reader.onload = (event: any) => {
                this.localUrlBack = event.target.result;
            }
            reader.readAsDataURL(event.target.files[0]);
        }
    }

    showPreviewFront(event: any) {
        if (event.target.files && event.target.files[0]) {
            var reader = new FileReader();
            reader.onload = (event: any) => {
                this.localUrlFront = event.target.result;
            }
            reader.readAsDataURL(event.target.files[0]);
        }
    }

 

    onUploadBack(event: any) {
        if (event.target.files.length > 0) {
            this.selectFileAvatar = event.target.files[0];
            this.addCustomerForm.get('fileBack').setValue(this.selectFileAvatar);
        }
    }

    onUploadFront(event: any) {
        if (event.target.files.length > 0) {
            let uploadData = new FormData();
            this.selectFileAvatar = event.target.files[0];
            this.addCustomerForm.get('fileFront').setValue(this.selectFileAvatar);
        }
    }
}
