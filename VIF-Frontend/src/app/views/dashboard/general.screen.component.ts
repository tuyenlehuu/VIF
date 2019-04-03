import { Component, OnInit, Input, AfterViewChecked } from '@angular/core';
import { Router } from '@angular/router';
import { AssetService } from '../../services/asset.service';
import { first } from 'rxjs/operators';
import { Asset } from '../../models/Asset.model';
import { Dashboard } from '../../models/Dashboard.model';

@Component({
    selector: 'general-screen',
    templateUrl: 'general.screen.component.html'
})
export class GeneralScreenComponent implements OnInit, AfterViewChecked {
  assets: Asset[] = [];
  @Input() 
  dashboard: Dashboard;

  constructor(private assetService:AssetService){}

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
}
