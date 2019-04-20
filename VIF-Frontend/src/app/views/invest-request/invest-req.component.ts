import { Component, OnInit, TemplateRef } from '@angular/core';
import { first, catchError } from 'rxjs/operators';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { ToastrService } from 'ngx-toastr';
import { config } from '../../config/application.config';
import { ResponseObject } from '../../models/Response.model';
import { Customer } from '../../models/Customer.model';
import { FormBuilder } from '@angular/forms';
import { User } from '../../models/User.model';
import { UserService } from '../../services/user.service';
import { Pager } from '../../models/Pager';
import { InvestRequest } from '../../models/InvestRequest.model'
import { InvestRequestService } from '../../services/invest.request.service';


@Component({
    templateUrl: 'invest-req.component.html',
    styleUrls: ['invest-req.component.scss']

})
export class InvestRequestComponent implements OnInit {
    isBuyScreen: boolean;
    users: User[] = [];
    user: User = new User();
    customer: Customer = new Customer();
    submitted = false;
    amountCCQAvaiable: number;
    investRequest: InvestRequest = new InvestRequest();
    CCQ: number = 0;
    date = new Date();
    constructor(private toastrService: ToastrService, private userService: UserService, private requestService: InvestRequestService) {
    }



    
    ngOnInit(): void {
        this.isBuyScreen = true;
        var pager: Pager = new Pager();
        var x: string;
        var currentUser = localStorage.getItem("currentUser");
        var u = JSON.parse(currentUser);
        x = u.username.toString();
        this.user.username = x;
        pager.page = 1;
        this.userService.getUsersByCondition(this.user, pager).pipe(first()).subscribe((res: any) => {
            this.users = res.data;
            //console.log("cusssss", this.users);
            this.user = this.users[0];
            //console.log("userRR", this.user);
            this.customer = this.user.customer;
            //console.log(this.customer);
            this.amountCCQAvaiable = this.customer.totalCcq;
        });
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

    changeScreen(typeScreen: number) {
        if (typeScreen === 1) {
            this.isBuyScreen = true;
            this.CCQ = 0;
        } else {
            this.isBuyScreen = false;
            this.CCQ = 0;
        }
    }

    buyCCQ() {
        this.investRequest.typeOfRequest = 1;
        this.investRequest.customer = this.customer;
        this.investRequest.amount = this.CCQ;
        this.investRequest.createDate = this.date;
        console.log("-------", this.investRequest);
        this.requestService.add(this.investRequest).subscribe((res: any) => {
            if (res != null) {
                this.showSuccess("Gửi yêu cầu thành công");
            }
            () => {
                this.showError("Gửi yêu cầu thất bại");
            }
        });
    }

    sellCCQ() {
        this.investRequest.typeOfRequest = 2;
        this.investRequest.customer = this.customer;
        this.investRequest.amount = this.CCQ;
        this.investRequest.createDate = this.date;
        console.log("-------", this.investRequest);
        this.requestService.add(this.investRequest).subscribe((res: any) => {
            console.log("logggg", res);
            if (res != null) {
                this.showSuccess("Gửi yêu cầu thành công");
            }
            () => {
                this.showError("Gửi yêu cầu thất bại");
            }
        });

    }

    saveCCQ() {
        if (this.isBuyScreen == true && this.CCQ != 0) {
            this.buyCCQ();

        } else {

            if (this.CCQ > this.amountCCQAvaiable || this.CCQ == 0) {

                return this.showError("Thất bại! Vui lòng nhập lại CCQ!");

            }
            return this.sellCCQ();

        }

    }

    resetForm() {
        this.CCQ = 0;

    }



}
