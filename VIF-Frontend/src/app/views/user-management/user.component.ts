import { Component, OnInit, TemplateRef } from '@angular/core';
import { User } from '../../models/User.model';
import { UserService } from '../../services/user.service';
import { first, catchError } from 'rxjs/operators';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { error } from '@angular/compiler/src/util';
import { ToastrService } from 'ngx-toastr';
import { config } from '../../config/application.config';
import { ResponseObject } from '../../models/Response.model';
import { Pager } from '../../models/Pager';

@Component({
    templateUrl: 'user.component.html'
})
export class UserComponent implements OnInit {
    users: User[] = [];
    modalRef: BsModalRef;
    userSearch: User = new User();

    p: number = 1;
    total: number;
    pageSize: number = 5;
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

    constructor(private userService: UserService, private modalService: BsModalService, private toastrService: ToastrService) { }

    ngOnInit(): void {
        // this.userService.getAll().pipe(first()).subscribe((respons: any) => {
        //     // console.log("data: ", respons);
        //     this.users = respons.data;
        // });
        // this.search();
        this.getPage(1);
    }

    getPage(page: number) {
        var pager: Pager = new Pager();
        pager.page = page;
        pager.pageSize = this.pageSize;
        this.userService.getUsersByCondition(this.userSearch, pager).pipe(first()).subscribe((respons: any) => {
            this.users = respons.data;
            this.total = respons.totalRow;
            this.p = page;
        });
    }

    confirmDel(template: TemplateRef<any>, userId: string) {
        this.modalRef = this.modalService.show(template);
        this.modalRef.content = userId;
    }

    deleteUser() {

        this.userService.deleteById(this.modalRef.content).subscribe(res => {
            this.showSuccess('Xóa thành công');
            // this.userService.getAll().pipe(first()).subscribe((respons: any) => {
            //     this.users = respons.data;
            // });
            this.getPage(1);
        }, catchError => {
            // console.log("result: ", catchError);
        });
        this.modalRef.hide();
    }

    showSuccess(mes: string) {
        this.toastrService.success('', mes, {
            timeOut: config.timeoutToast
        });
    }

    search() {
        this.getPage(1);
    }
}
