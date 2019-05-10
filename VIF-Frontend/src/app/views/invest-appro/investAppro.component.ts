import { Component, OnInit, TemplateRef } from '@angular/core';
import { first, catchError } from 'rxjs/operators';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { ToastrService } from 'ngx-toastr';
import { config } from '../../config/application.config';
import { ResponseObject } from '../../models/Response.model';
import { Customer } from '../../models/Customer.model';
import { FormBuilder } from '@angular/forms';
import { Pager } from '../../models/Pager';
import { InvestAppro } from '../../models/InvestAppro.model'
import { InvestApproService } from '../../services/investAppro.service';
import { formatDate } from '../../helpers/function.share';
import { CustomerService } from '../../services/customer.service';

@Component({
  templateUrl: 'investAppro.component.html',

})
export class InvestApproComponent implements OnInit {
  investAppros: InvestAppro[] = [];
  customers: Customer[] = [];
  p: number = 1;
  total: number;
  pageSize: number = 5;
  investApproSearch: InvestAppro = new InvestAppro;
  fromDate: Date;
  toDate: Date;

  constructor(private investApproService: InvestApproService, private customerService: CustomerService, private modalService: BsModalService, private toastrService: ToastrService, ) {
  }

  ngOnInit(): void {
    this.customerService.getAll().pipe(first()).subscribe((res: any) => {
      this.customers = res;
    });
    this.search();;
  }
  getPage(page: number) {
    var pager: Pager = new Pager();
    pager.page = page;
    pager.pageSize = this.pageSize;
    this.investApproService.getInvestApproByCondition(this.investApproSearch, formatDate(this.fromDate), formatDate(this.toDate), pager).pipe(first()).subscribe((respons: any) => {
      this.investAppros = respons.data;
      this.total = respons.totalRow;
      this.p = page;
      // console.log("data: ", respons);
    });
  }
  search() {
    this.getPage(1);
}
}