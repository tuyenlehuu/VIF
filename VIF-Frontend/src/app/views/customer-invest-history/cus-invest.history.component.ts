import { Component, OnInit, TemplateRef } from '@angular/core';
import { first } from 'rxjs/operators';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { ToastrService } from 'ngx-toastr';
import { config } from '../../config/application.config';
import { ResponseObject } from '../../models/Response.model';
import { CustomerService } from '../../services/customer.service';
import { Customer } from '../../models/Customer.model';
import { InvestorTransService } from '../../services/investor.transaction.service';
import { BsDatepickerConfig } from 'ngx-bootstrap/datepicker';
import { InvestorHistory } from '../../models/InvestorHistory.model';
import { Pager } from '../../models/Pager';

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
    investorHisLst: InvestorHistory[] = [];
    customerSelectedId: number;
    fromDate: string;
    toDate: string;
    p: number = 1;
    total: number;
    pageSize: number = 5;


    constructor(private toastrService: ToastrService, private customerService: CustomerService, private investorTransService: InvestorTransService) {      
        this.bsConfig = Object.assign({}, { containerClass: this.colorTheme });
    }

    ngOnInit(): void {
        this.customerService.getAll().pipe(first()).subscribe((respons: any) => {
            // console.log("data: ", respons);
            this.customers = respons;
        });
        this.search();
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

    search(){
        this.getPage(1);
    }

    getPage(page: number) {
        var pager: Pager = new Pager();
        pager.page = page;
        pager.pageSize = this.pageSize;
        this.investorTransService.searchInvestorHistoryByCondition(this.customerSelectedId, this.fromDate, this.toDate, pager).pipe(first()).subscribe((respons: any) => {
            this.investorHisLst = respons.data;
            this.total = respons.totalRow;
            this.p = page;
            // console.log("data: ", respons);
        });
    }

}
