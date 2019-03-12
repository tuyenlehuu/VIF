import { Component, OnInit, TemplateRef } from '@angular/core';
import { User } from '../../models/User.model';
import { UserService } from '../../services/user.service';
import { first, catchError } from 'rxjs/operators';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { error } from '@angular/compiler/src/util';
import { ToastrService } from 'ngx-toastr';
import { config } from '../../config/application.config';
import { ResponseObject } from '../../models/Response.model';

@Component({
  templateUrl: 'user.component.html'
})
export class UserComponent implements OnInit {
  users: User[] = [];
  modalRef: BsModalRef;
  myId = 1991;

  constructor(private userService:UserService, private modalService: BsModalService, private toastrService: ToastrService){}

  ngOnInit(): void {
    this.userService.getAll().pipe(first()).subscribe((respons: any)=>{
      console.log("data: ", respons);
      this.users = respons.data;
    });
  }

  confirmDel(template: TemplateRef<any>, userId: string){
    this.modalRef = this.modalService.show(template);
    this.modalRef.content = userId;
  }

  deleteUser(){
    // console.log("Start delete: ", this.modalRef.content);
    this.userService.deleteById(this.modalRef.content).subscribe(res=>{
      this.showSuccess('Cập nhật thành công');
      this.userService.getAll().pipe(first()).subscribe(users=>{
        this.users = users;
      });
    }, catchError=>{
      console.log("result: ", catchError);
    });
    this.modalRef.hide();
  }

  showSuccess(mes: string) {
    this.toastrService.success('', mes, {
        timeOut: config.timeoutToast
    });
}
}
