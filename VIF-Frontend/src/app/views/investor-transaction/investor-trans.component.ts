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
    templateUrl: 'investor-trans.component.html',
    styleUrls: ['investor-trans.component.scss']
})
export class InvestorTransComponent implements OnInit {
    isBuyScreen: boolean = true;

    constructor(private modalService: BsModalService, private toastrService: ToastrService) { }

    ngOnInit(): void {
        // this.userService.getAll().pipe(first()).subscribe((respons: any) => {
        //     // console.log("data: ", respons);
        //     this.users = respons.data;
        // });
        // this.search();
    }

    showSuccess(mes: string) {
        this.toastrService.success('', mes, {
            timeOut: config.timeoutToast
        });
    }

    changeScreen(typeScreen: number){
        if(typeScreen === 1){
            this.isBuyScreen = true;
        }else{
            this.isBuyScreen = false;
        }
    }
}
