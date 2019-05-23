import { Component, OnInit, Input, AfterViewChecked } from '@angular/core';
import { DashboardService } from '../../services/dashboard.service';
import { first } from 'rxjs/operators';
import { Asset } from '../../models/Asset.model';
import { AssetService } from '../../services/asset.service';
import { CustomerService } from '../../services/customer.service';

@Component({
  selector: 'debt-screen',
  templateUrl: 'debt.vif.component.html'
})
export class DebtScreenComponent implements OnInit {

  assets: Asset [] = [];
  
  constructor(private dashboardService: DashboardService, private assetService: AssetService, private customerService: CustomerService) {
    // this.search();
  }

  ngOnInit(): void {
    this.dashboardService.getDebtReportData('tuyenlh').pipe(first()).subscribe((res: any) => {
      // console.log("res: ", res);
      if(res){
        this.assets = res;
      }
    });
  }
}


