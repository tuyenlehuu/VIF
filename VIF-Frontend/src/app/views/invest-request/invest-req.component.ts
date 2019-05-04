import { Component, OnInit, TemplateRef } from '@angular/core';
import { first, catchError } from 'rxjs/operators';
import { ToastrService } from 'ngx-toastr';
import { config } from '../../config/application.config';
import { Customer } from '../../models/Customer.model';
import { User } from '../../models/User.model';
import { UserService } from '../../services/user.service';
import { Pager } from '../../models/Pager';
import { InvestRequest } from '../../models/InvestRequest.model'
import { InvestRequestService } from '../../services/invest.request.service';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { ValidateSellAmount, NotEqualZero } from '../../helpers/function.share';


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
    date = new Date();
    CCQ: number = 0;
    CCQTemp: number = 0;
    moneyTemp: number = 0;
    money: number = 0;
    price: number = 0;
    constructor(private toastrService: ToastrService, private userService: UserService, 
        private requestService: InvestRequestService, private fb: FormBuilder) {
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
            this.user = this.users[0];
            this.customer = this.user.customer;
            this.amountCCQAvaiable = this.customer.totalCcq;
            

        });
        this.requestService.getPriceCCQ().subscribe((res: any) => {
            this.price = res;
            this.investRequest.price = res;
            
        })
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
            this.resetForm();
        } else {
            this.isBuyScreen = false;
            this.resetForm();
        }
    }

    buyCCQ() {
        this.investRequest.amount = this.CCQTemp;
        this.investRequest.typeOfRequest = 1;
        this.investRequest.customer = this.customer;
        this.investRequest.money = this.money;
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
        this.investRequest.money = this.moneyTemp;
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
        if (this.isBuyScreen == true) {
            if (this.money == 0) {
                return this.showError("Thất bại! Số tiền không thể bằng 0!");
            }
            return this.buyCCQ();

        } else {

            if (this.CCQ > this.customer.totalCcq || this.CCQ == 0) {

                return this.showError("Thất bại! Vui lòng nhập lại CCQ!");

            }
            return this.sellCCQ();

        }

    }

    resetForm() {
        this.CCQTemp = 0;
        this.moneyTemp = 0;
        this.CCQ = 0;
        this.money = 0;
        this.amountCCQAvaiable = this.customer.totalCcq;
    }
    onKeyMoney(event: any) {
        this.CCQTemp = Number((this.money / (this.price * 1000)).toFixed(2));
    

    }

    onKeyCCQ(event: any) {
        this.moneyTemp = Number((this.CCQ * this.price * 1000).toFixed(2));
        this.amountCCQAvaiable = Number((this.customer.totalCcq - this.CCQ).toFixed(2));

    }



}
