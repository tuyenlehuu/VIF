import { Component, OnInit } from '@angular/core';
import { User } from '../../models/User.model';
import { UserService } from '../../services/user.service';
import { first } from 'rxjs/operators';
import { Router, ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { config } from '../../config/application.config';

@Component({
    templateUrl: 'create.edit.user.component.html'
})
export class CEUserComponent implements OnInit {
    user: User;
    sub: any;
    id: any;
    roles = [
        {
            name: 'Chọn quyền',
            value: '-1'
        },
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
            name: 'Chọn trạng thái',
            value: -1
        },
        {
            name: 'Hoạt động',
            value: 1
        },
        {
            name: 'Ngừng hoạt động',
            value: 0
        }
    ];

    constructor(private route: ActivatedRoute, private userService: UserService, private router: Router, private toastrService: ToastrService) { }

    ngOnInit(): void {
        // this.sub = this.route
        //     .data
        //     .subscribe(v => console.log(v));
        this.id = this.route.snapshot.params['id'];
        if (this.id > 0) {
            this.userService.getById(this.id).subscribe((res: User) => {
                this.user = res;
                console.log("current user: ", this.user);
            })
        } else {
            this.user = new User();
        }
    }

    saveUser() {
        if (this.id > 0) {
            // update user
            this.userService.update(this.user).subscribe(res => {
                // console.log("new user: ", res);
                this.showSuccess('Cập nhật thành công');
                this.router.navigate(['/user-management']);
            }, (err) => {
                this.showError('Cập nhật user không thành công!');
                console.log(err);
            });
        } else {
            this.userService.register(this.user).subscribe(res => {
                this.showSuccess('Thêm mới thành công');
                this.router.navigate(['/user-management']);
            }, (err) => {
                this.showError('Thêm mới user không thành công!');
                console.log(err);
            });
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
}
