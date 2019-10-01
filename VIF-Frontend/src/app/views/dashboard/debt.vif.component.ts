import { Component, OnInit, Input, AfterViewChecked } from '@angular/core';
import { DashboardService } from '../../services/dashboard.service';
import { first } from 'rxjs/operators';
import { Asset } from '../../models/Asset.model';
import { AssetService } from '../../services/asset.service';
import { CustomerService } from '../../services/customer.service';
import { config } from '../../config/application.config';
import { User } from '../../models/User.model';

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
    // get user login
    let currentUser = localStorage.getItem(config.currentUser);
    if(currentUser){
      var mUser: User = JSON.parse(currentUser);
      if(mUser){
        let username = mUser.username;
        if(username){
          this.dashboardService.getDebtReportData(username).pipe(first()).subscribe(res => {
            // console.log("res: ", res);
            if(res){
              this.assets = res;
            }
          });
        }
        // if(mUser.role === "ROLE_ADMIN"){
        //   this.navItems = navItems;
        // }else{
        //   this.navItems = navItemsUser;
        // }
      }
    }
    
  }
}


