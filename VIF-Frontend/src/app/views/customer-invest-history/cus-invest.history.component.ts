import { Component, OnInit, TemplateRef } from '@angular/core';
import { first, catchError } from 'rxjs/operators';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { ToastrService } from 'ngx-toastr';
import { config } from '../../config/application.config';
import { ResponseObject } from '../../models/Response.model';
import { CustomerService } from '../../services/customer.service';
import { Customer } from '../../models/Customer.model';
import { InvestorTransService } from '../../services/investor.transaction.service';
import { BsDatepickerConfig } from 'ngx-bootstrap/datepicker';

@Component({
    templateUrl: 'cus-invest-his.component.html',
    styleUrls: ['cus-invest-his.component.scss']
})
export class CusInvestHistoryComponent implements OnInit {
    customers: Customer[] = [];
    responseObject: ResponseObject;
    date: any;
    bsConfig: Partial<BsDatepickerConfig>;
    colorTheme = "theme-blue";

    constructor(private toastrService: ToastrService, private customerService: CustomerService, private investorTransService: InvestorTransService) {      
        this.bsConfig = Object.assign({}, { containerClass: this.colorTheme });
    }

    ngOnInit(): void {
        this.customerService.getAll().pipe(first()).subscribe((respons: any) => {
            // console.log("data: ", respons);
            this.customers = respons;
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

}
