import { Component, OnInit } from '@angular/core';
import { User } from '../../models/User.model';
import { UserService } from '../../services/user.service';
import { first } from 'rxjs/operators';
import { Router, ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { config } from '../../config/application.config';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MustMatch, RequireCombo } from '../../helpers/function.share';

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

    constructor(private route: ActivatedRoute, private userService: UserService, private router: Router, private toastrService: ToastrService, private fb: FormBuilder) {
        this.createForm();
    }

    ngOnInit(): void {
        // this.sub = this.route
        //     .data
        //     .subscribe(v => console.log(v));
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
            username: ['', Validators.required],
            password: ['', [Validators.required, Validators.minLength(8)]],
            email: ['', Validators.required, Validators.email],
            confirmPassword: ['', [Validators.required, Validators.minLength(8)]],
            role: ['ROLE_USER', Validators.required],
            status: [1, Validators.required]
        }, {
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
        if (this.id > 0) {
            // update user
            this.userService.update(user).subscribe(res => {
                // console.log("new user: ", res);
                this.showSuccess('Cập nhật thành công');
                this.router.navigate(['/user-management']);
            }, (err) => {
                this.showError('Cập nhật user không thành công!');
                console.log(err);
            });
        } else {
            this.userService.register(user).subscribe(res => {
                this.showSuccess('Thêm mới thành công');
                this.router.navigate(['/user-management']);
            }, (err) => {
                this.showError('Thêm mới user không thành công!');
                console.log(err);
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
        this.saveUser(this.user);
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
}
