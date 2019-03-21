import { Component, OnInit, TemplateRef } from '@angular/core';
import { User } from '../../models/User.model';
import { UserService } from '../../services/user.service';
import { first, catchError } from 'rxjs/operators';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { error } from '@angular/compiler/src/util';
import { ToastrService } from 'ngx-toastr';
import { config } from '../../config/application.config';
import { ResponseObject } from '../../models/Response.model';
import { CustomerService } from '../../services/customer.service';
import { Customer } from '../../models/Customer.model';

@Component({
    templateUrl: 'investor-trans.component.html',
    styleUrls: ['investor-trans.component.scss']
})
export class InvestorTransComponent implements OnInit {
    isBuyScreen: boolean = true;
    customers: Customer[] = [];
    customerSelectedId: number;

    constructor(private modalService: BsModalService, private toastrService: ToastrService, private customerService: CustomerService) { }

    ngOnInit(): void {
        this.customerService.getAll().pipe(first()).subscribe((respons: any) => {
            // console.log("data: ", respons);
            this.customers = respons;
            if(this.customers){
                this.customerSelectedId = this.customers[0].id;
            }
        });
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

    buyCCQ(){
        console.log("this.customerSelectedId", this.customerSelectedId);
    }

    sellCCQ(){

    }

    saveCCQ(){
        if(this.isBuyScreen){
            this.buyCCQ();
        }else{
            this.sellCCQ();
        }
    }

    cancelProcess(){
        if(this.isBuyScreen){

        }else{

        }
    }
}
