import { Component, OnInit } from '@angular/core';
import { User } from '../../models/User.model';
import { UserService } from '../../services/user.service';
import { first } from 'rxjs/operators';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
    templateUrl: 'create.edit.user.component.html'
})
export class CEUserComponent implements OnInit {
    user: User = new User();
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

    constructor(private route: ActivatedRoute, private userService: UserService, private router: Router) { }

    ngOnInit(): void {
        // this.sub = this.route
        //     .data
        //     .subscribe(v => console.log(v));
        this.id = this.route.snapshot.params['id'];
        this.userService.getById(this.id).subscribe((res: User)=>{
            this.user = res;
        })
    }

    addNewUser(){
        // this.userService.register(this.user).subscribe(res => {
        //     this.router.navigate(['/user-management']);
        //   }, (err) => {
        //     console.log(err);
        //   });
        console.log("current user: ", this.user);
    }
}
