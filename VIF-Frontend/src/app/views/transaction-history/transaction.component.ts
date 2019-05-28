import { Component, OnInit, TemplateRef } from '@angular/core';
import { first, catchError } from 'rxjs/operators';
import { ToastrService } from 'ngx-toastr';
import { config } from '../../config/application.config';
import { Pager } from '../../models/Pager';


import { ResponseObject } from '../../models/Response.model';
import { Asset } from '../../models/Asset.model';
import { BsDatepickerConfig } from 'ngx-bootstrap';
import { TransactionService } from '../../services/transaction.service';
import { formatDate } from '../../helpers/function.share';
import { AssetService } from '../../services/asset.service';
import { TransactionHistory } from '../../models/TransactionHistory';

@Component({
  templateUrl: 'transaction.component.html',
  styleUrls: ['transaction.component.css']
})
export class TransactionComponent implements OnInit {
  assets: Asset[] = [];
  transactionSearch: TransactionHistory = new TransactionHistory();
  responseObject: ResponseObject;
  date: any;
  bsConfig: Partial<BsDatepickerConfig>;
  colorTheme = "theme-blue";
  fromDate: Date;
  toDate: Date;
  p: number = 1;
  total: number;
  pageSize: number = 5;
  Transactions: TransactionHistory[] = [];
  assetID: number;
  typeOfTransaction1: string;
  status = [
    {
      name: 'Chọn loại',
      value: '-1'
    },
    {
      name: 'Mua',
      value: 'M'
    },
    {
      name: 'Bán',
      value: 'B'
    },
    {
      name: 'Cổ tức tiền',
      value: 'C'
    },
    {
      name: 'Cổ tức cổ phiếu',
      value: 'S'
    }
  ];

  ngOnInit(): void {
    this.assetService.getAll().pipe(first()).subscribe((res: any) => {
      this.assets = res.data;
    });

    this.getPage(1);


  }

  constructor(private toastrService: ToastrService, private assetService: AssetService, private transactionService: TransactionService) {
    this.bsConfig = Object.assign({}, { containerClass: this.colorTheme });

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
  search() {
    if(!this.fromDate && !this.toDate){
      this.getPage(1);
    }
     else if(!this.fromDate){
       this.showError("Mời bạn nhập ngày bắt đầu");
     }else if(!this.toDate){
       this.showError("Mời bạn nhập ngày tới");
     }else if(this.fromDate && this.toDate){
       var time=(this.toDate.getTime()-this.fromDate.getTime())/1000;
       if(time<0){
         this.showError("Ngày kết thúc nhỏ hơn ngày tới, vui lòng nhập lại!!!");
       }else{
         this.getPage(1);
       }
     }
  }

  getPage(page: number) {
    var pager: Pager = new Pager();
    pager.page = page;
    pager.pageSize = this.pageSize;
    this.transactionService.getTransactionsByCondition(formatDate(this.fromDate), formatDate(this.toDate), this.transactionSearch.typeOfTransaction, this.assetID, pager).pipe(first()).subscribe((respons: any) => {
      this.Transactions = respons.data;
      this.total = respons.totalRow;
      this.p = page;
    });

  }
  exportCSV() {
    this.transactionService.exportCsv(formatDate(this.fromDate), formatDate(this.toDate), this.transactionSearch.typeOfTransaction, this.assetID);
    // this.investorTransService.exportCsv(this.customerSelectedId, this.fromDate, this.toDate).subscribe(respons => this.downloadFile(respons),
    //     error => console.log('Error downloading the file.'),
    //     () => console.info('OK'));
  }
  enterOnSubmitDate(){
    if(this.fromDate){
      console.log("formDate",this.fromDate);
      this.getPage(1);
    }
    if(this.toDate){
      this.getPage(1);
      console.log("toDate",this.toDate);
    }

  }

}
