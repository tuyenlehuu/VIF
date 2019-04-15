import { Component, OnInit, TemplateRef } from '@angular/core';
import { first, catchError } from 'rxjs/operators';
import { ToastrService } from 'ngx-toastr';
import { config } from '../../config/application.config';
import { Pager } from '../../models/Pager';


import { ResponseObject } from '../../models/Response.model';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
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
  responseObject: ResponseObject;
  date: any;
  bsConfig: Partial<BsDatepickerConfig>;
  colorTheme = "theme-red";
  fromDate: Date;
  p: number = 1;
  total: number;
  pageSize: number = 5;
  Transactions:TransactionHistory[]=[];
  assetID:number;
  typeOfTransaction1:string;
  status = [
    
    {
        name: 'Đầu tư',
        value: 'M'
    },
    {
        name: 'Rút vốn',
        value: 'A'
    }
];
  
  ngOnInit(): void {
    this.assetService.getAll().pipe(first()).subscribe((res:any)=>{
      this.assets=res.data;
    });
    this.getPage(1);


  }

  constructor(private toastrService: ToastrService, private assetService:AssetService, private transactionService:TransactionService) {
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
  search(){
    this.getPage(1);
  }
  
  getPage(page: number) {
    var pager: Pager = new Pager();
    pager.page = page;
    pager.pageSize = this.pageSize;
    this.transactionService.getTransactionsByCondition(formatDate(this.fromDate),this.typeOfTransaction1,this.assetID,pager).pipe(first()).subscribe((respons:any)=>{
      this.Transactions=respons.data;
      this.total = respons.totalRow;
      this.p = page;
    });
   
  }
  exportCSV() {
    this.transactionService.exportCsv(formatDate(this.fromDate),this.typeOfTransaction1,this.assetID);
    // this.investorTransService.exportCsv(this.customerSelectedId, this.fromDate, this.toDate).subscribe(respons => this.downloadFile(respons),
    //     error => console.log('Error downloading the file.'),
    //     () => console.info('OK'));
}

}
