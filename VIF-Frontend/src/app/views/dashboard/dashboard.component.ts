import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Dashboard } from '../../models/Dashboard.model';
import { DashboardService } from '../../services/dashboard.service';
import { first } from 'rxjs/operators';

@Component({
  templateUrl: 'dashboard.component.html',
  styleUrls: ['dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  dashboard: Dashboard = new Dashboard();

  constructor(private dashboardService:DashboardService){
    this.dashboardService.getData().pipe(first()).subscribe(dashboard=>{
      this.dashboard = dashboard;
      // console.log("dashboard is: ", dashboard);
    });
  }

  ngOnInit(): void {
    // generate random values for mainChart
    
  }
}
