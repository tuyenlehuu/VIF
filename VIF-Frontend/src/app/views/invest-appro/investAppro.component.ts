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
import { BsDatepickerConfig } from 'ngx-bootstrap';

@Component({
  templateUrl: 'investAppro.component.html',

})
export class InvestApproComponent implements OnInit {
  investAppros: InvestAppro[] = [];
  customers: Customer[] = [];
  customerId: number;
  responseObject: ResponseObject;
  p: number = 1;
  total: number;
  bsConfig: Partial<BsDatepickerConfig>;
  pageSize: number = 5;
  investApproSearch: InvestAppro = new InvestAppro();
  fromDate: Date;
  toDate: Date;

  typeOfRequest = [
    {
      name: 'Chọn Loại',
      value: 0
    },
    {
      name: 'Mua',
      value: 1
    },
    {
      name: 'Bán',
      value: 2
    }
  ];

  constructor(private investApproService: InvestApproService, private customerService: CustomerService, private modalService: BsModalService, private toastrService: ToastrService, ) {
  }

  ngOnInit(): void {
    this.investApproService.getAll().pipe(first()).subscribe((res: any) => {
      this.investAppros = res;
      //console.log("data: ", res);
    });
    this.search();
  }
  search() {
    this.getPage(1);
  }
  getPage(page: number) {
    var pager: Pager = new Pager();
    pager.page = page;
    pager.pageSize = this.pageSize;
    this.investApproService.getInvestRequestByCondition(this.investApproSearch, formatDate(this.fromDate), formatDate(this.toDate), pager).pipe(first()).subscribe((respons: any) => {
      this.investAppros = respons;
      this.total = respons.totalRow;
      this.p = page;
      console.log("data: ", respons);
    });
  }
}