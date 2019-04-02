import { Component, OnInit, Input, AfterViewChecked } from '@angular/core';
import { Router } from '@angular/router';
import { getStyle, hexToRgba } from '@coreui/coreui/dist/js/coreui-utilities';
import { CustomTooltips } from '@coreui/coreui-plugin-chartjs-custom-tooltips';
import { User } from '../../models/User.model';
import { DashboardService } from '../../services/dashboard.service';
import { first } from 'rxjs/operators';
import { Dashboard } from '../../models/Dashboard.model';

@Component({
    selector: 'nav-screen',
    templateUrl: 'nav.vif.component.html'
})
export class NAVScreenComponent implements OnInit {
  dashboard: Dashboard;

  // Line NAV
  // lineChart
  public lineChartData: Array<any> = [
    {data: [10000.52, 10000.61, 10000.13, 10000.22, 10000.10, 10000.20, 10000.14,10000.52, 10000.61, 10000.13, 10000.22, 10000.10, 10000.20, 10000.14,10000.52, 10000.61, 10000.13, 10000.22, 10000.10, 10000.20, 10000.14,10000.52, 10000.61, 10000.13, 10000.22, 10000.10, 10000.20, 10000.14,10000.52, 10000.61, 10000.13, 10000.22, 10000.10, 10000.20, 10000.14,10000.52, 10000.61, 10000.13, 10000.22, 10000.10, 10000.20, 10000.14,10000.52, 10000.61, 10000.13, 10000.22, 10000.10, 10000.20, 10000.14,10000.52, 10000.61, 10000.13, 10000.22, 10000.10, 10000.20, 10000.14,10000.52, 10000.61, 10000.13, 10000.22, 10000.10, 10000.20, 10000.14,10000.52, 10000.61, 10000.13, 10000.22, 10000.10, 10000.20, 10000.14,10000.52, 10000.61, 10000.13, 10000.22, 10000.10, 10000.20, 10000.14,10000.52, 10000.61, 10000.13, 10000.22, 10000.10, 10000.20, 10000.14,10000.52, 10000.61, 10000.13, 10000.22, 10000.10, 10000.20, 10000.14,10000.52, 10000.61, 10000.13, 10000.22, 10000.10, 10000.20, 10000.14,10000.52, 10000.61, 10000.13, 10000.22, 10000.10, 10000.20, 10000.14,10000.52, 10000.61, 10000.13, 10000.22, 10000.10, 10000.20, 10000.14], label:'NAV'}
  ];
  public lineChartLabels: Array<any> = ['January', 'February', 'March', 'April', 'May', 'June', 'July','January', 'February', 'March', 'April', 'May', 'June', 'July','January', 'February', 'March', 'April', 'May', 'June', 'July','January', 'February', 'March', 'April', 'May', 'June', 'July','January', 'February', 'March', 'April', 'May', 'June', 'July','January', 'February', 'March', 'April', 'May', 'June', 'July','January', 'February', 'March', 'April', 'May', 'June', 'July','January', 'February', 'March', 'April', 'May', 'June', 'July','January', 'February', 'March', 'April', 'May', 'June', 'July','January', 'February', 'March', 'April', 'May', 'June', 'July','January', 'February', 'March', 'April', 'May', 'June', 'July','January', 'February', 'March', 'April', 'May', 'June', 'July','January', 'February', 'March', 'April', 'May', 'June', 'July','January', 'February', 'March', 'April', 'May', 'June', 'July','January', 'February', 'March', 'April', 'May', 'June', 'July','January', 'February', 'March', 'April', 'May', 'June', 'July'];
  public lineChartOptions: any = {
    animation: false,
    responsive: true
  };
  public lineChartColours: Array<any> = [
    { // grey
      backgroundColor: '#fff',
      borderColor: 'rgb(105, 195, 255)',
      pointBackgroundColor: 'rgb(255, 139, 164)',
      pointBorderColor: 'rgb(255, 139, 164)',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(148,159,177,0.8)',
      fill: false
    },
    { // dark grey
      backgroundColor: 'rgba(77,83,96,0.2)',
      borderColor: 'rgba(77,83,96,1)',
      pointBackgroundColor: 'rgba(77,83,96,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(77,83,96,1)'
    },
    { // grey
      backgroundColor: 'rgba(148,159,177,0.2)',
      borderColor: 'rgba(148,159,177,1)',
      pointBackgroundColor: 'rgba(148,159,177,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(148,159,177,0.8)'
    }
  ];
  public lineChartLegend = true;
  public lineChartType = 'line';


  constructor(private dashboardService:DashboardService){
    this.dashboardService.getData().pipe(first()).subscribe(dashboard=>{
      this.dashboard = dashboard;
      if(this.dashboard){
      }
    });
  }

  ngOnInit(): void {
  }

  
  // events
  public chartClicked(e: any): void {
    console.log(e);
  }

  public chartHovered(e: any): void {
    console.log(e);
  }
  

}


