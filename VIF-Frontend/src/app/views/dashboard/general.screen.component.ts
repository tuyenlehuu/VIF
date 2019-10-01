import { Component, OnInit, Input, AfterViewChecked } from '@angular/core';
import { Router } from '@angular/router';
import { AssetService } from '../../services/asset.service';
import { first } from 'rxjs/operators';
import { Asset } from '../../models/Asset.model';
import { Dashboard } from '../../models/Dashboard.model';
import { DashboardService } from '../../services/dashboard.service';
import { ToastrService } from 'ngx-toastr';
import { config } from '../../config/application.config';

@Component({
    selector: 'general-screen',
    templateUrl: 'general.screen.component.html'
})
export class GeneralScreenComponent implements OnInit, AfterViewChecked {
  assets: Asset[] = [];
  @Input() 
  dashboard: Dashboard;

  constructor(private assetService:AssetService, private dashboardService: DashboardService, private toastrService: ToastrService){}

  ngOnInit(): void {
    // generate random values for mainChart
    this.assetService.getAllShares().pipe(first()).subscribe(respon=>{
      this.assets = respon.data;
      // console.log("all assets is: ", this.assets);
    });
  }

  ngAfterViewChecked() {
    // console.log("dashboard: ", this.dashboard);
  }

  updatePrice(){
    this.dashboardService.updatePriceFromMarket().pipe(first()).subscribe((respons: any) => {
      this.showSuccess("Cập nhật giá thành công!");
      this.assetService.getAllShares().pipe(first()).subscribe(respon=>{
        this.assets = respon.data;
        // console.log("all assets is: ", this.assets);
      });
    },(err) =>{
      this.showError("Cập nhật giá không thành công");
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
