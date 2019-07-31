import { Component, OnInit } from '@angular/core';
import { User } from '../../models/User.model';
import { Customer } from '../../models/Customer.model';
import { UserService } from '../../services/user.service';
import { first } from 'rxjs/operators';
import { Router, ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { config } from '../../config/application.config';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MustMatch, RequireCombo } from '../../helpers/function.share';
import { TranslateService } from '@ngx-translate/core';
import { CustomerService } from '../../services/customer.service';
import { forkJoin } from 'rxjs';

@Component({
    templateUrl: 'create.edit.user.component.html'
})
export class CEUserComponent implements OnInit {
    user: User;
    sub: any;
    id: any;
    addUserForm: FormGroup;
    submitted = false;
    editUserForm: FormGroup;
    customers: Customer[] = [];
    customerSelectedId: number;
    selectFileAvatar: File;
    localUrlAvatar: any[];


    // should load from DB (Lam sau)
    roles = [
        {
            name: 'Quản trị viên',
            value: 'ROLE_ADMIN'
        },
        {
            name: 'Nhà đầu tư',
            value: 'ROLE_USER'
        },
        {
            name: 'Khách',
            value: 'ROLE_GUEST'
        }
    ];

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

    constructor(private route: ActivatedRoute, private userService: UserService, private router: Router, 
        private toastrService: ToastrService, private fb: FormBuilder, private translateService: TranslateService, private customerService: CustomerService) {
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
        if (this.id > 0) {
            this.userService.getById(this.id).subscribe((res: User) => {
                this.user = res;
                // console.log("current user: ", this.user);
                this.createEditForm();
            })
        } else {
            this.user = new User();
        }
    }

    createForm() {
        this.addUserForm = this.fb.group({
            fileAvatar: [''],
            username: ['', Validators.required],
            password: ['', [Validators.required, Validators.minLength(8)]],
            email: ['', Validators.required],
            confirmPassword: ['', [Validators.required, Validators.minLength(8)]],
            role: ['ROLE_USER', Validators.required],
            status: [1, Validators.required],
            customerControl: [this.customerSelectedId, Validators.required]
        },{
            validator: [MustMatch('password', 'confirmPassword'), RequireCombo('role')]
        });
    }

    createEditForm() {
        this.editUserForm = this.fb.group({
            eUsername: [{ value: this.user.username, disabled: true }, Validators.required],
            eEmail: [this.user.email, Validators.required],
            eRole: [this.user.role, Validators.required],
            eStatus: [this.user.activeFlg, Validators.required]
        });
    }

    saveUser(user: User) {
        console.log("ussssser+>>",user.avatar);
        if (this.id > 0) {
            // update user
            this.userService.update(user).subscribe(res => {
                // console.log("new user: ", res);
                this.translateService.get('vif.message.update_success').subscribe((res: string) => {
                    this.showSuccess(res);
                });
                this.router.navigate(['/user-management']);
            }, (err) => {
                this.translateService.get('vif.message.update_failed').subscribe((res: string) => {
                    this.showError(res);
                });
            });
        } else {
            this.userService.register(user).pipe(first()).subscribe((respons: any) => {
                console.log("user===<<.<.,",user);
                if(respons.code === 409){
                    this.translateService.get('vif.message.user_exists').subscribe((res: string) => {
                        this.showError(res);
                    });
                }else if(respons.code === 200){
                    this.translateService.get('vif.message.create_success').subscribe((res: string) => {
                        this.showSuccess(res);
                    });
                    this.router.navigate(['/user-management']);
                }
            }, (err) => {
                this.translateService.get('vif.message.create_failed').subscribe((res: string) => {
                    this.showError(res);
                });
            });
        }
    }

    get addForm() { return this.addUserForm.controls; }

    get editForm() { return this.editUserForm.controls; }

    onAddSubmit() {
        this.submitted = true;
        // stop here if form is invalid
        if (this.addUserForm.invalid) {
            return;
        }
        this.user.username = this.addUserForm.value.username;
        this.user.password = this.addUserForm.value.password;
        this.user.activeFlg = this.addUserForm.value.status;
        this.user.email = this.addUserForm.value.email;
        this.user.role = this.addUserForm.value.role;
        let cus: Customer = new Customer();
        cus.id = this.addUserForm.value.customerControl;
        this.user.customer = cus;
        this.loadImage();
    }

    onEditSubmit() {
        if (this.editUserForm.invalid) {
            return;
        }

        this.user.password = this.editUserForm.value.ePassword;
        this.user.activeFlg = this.editUserForm.value.eStatus;
        this.user.email = this.editUserForm.value.eEmail;
        this.user.role = this.editUserForm.value.eRole;
        this.saveUser(this.user);
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

    onChangeCustomer() {
    }

    loadImage() {
        let uploadDataAvatar = new FormData();
        uploadDataAvatar.set('file', this.selectFileAvatar);

        forkJoin(
            this.userService.upFileAvatar(uploadDataAvatar)

        ).subscribe(([res]) => {
                this.user.avatar = res;
                this.checkCompleteElement();
        });
    }


    checkCompleteElement() {
        if (this.user.avatar != null) {
            this.saveUser(this.user);
            return;
        } else {
            this.showError('Chưa upload ảnh ');

        }
    }

    showPreviewAvatar(event: any) {
       // this.onUploadAvatar(event);
        if (event.target.files && event.target.files[0]) {
            this.selectFileAvatar = event.target.files[0];
           // this.addUserForm.get('fileAvatar').setValue(this.selectFileAvatar);
            var reader = new FileReader();
            reader.onload = (event: any) => {
                this.localUrlAvatar = event.target.result;
            }
            reader.readAsDataURL(event.target.files[0]);
        }
    }

    onUploadAvatar(event: any) {  
        if (event.target.files.length > 0) {
            
           
        }
        
    }

   

}
