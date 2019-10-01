import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Dashboard } from '../../models/Dashboard.model';
import { DashboardService } from '../../services/dashboard.service';
import { first } from 'rxjs/operators';
import { User } from '../../models/User.model';
import { config } from '../../config/application.config';

@Component({
    templateUrl: 'dashboard.component.html',
    styleUrls: ['dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
    dashboard: Dashboard = new Dashboard();
    isAdmin: boolean = false;

    constructor(private dashboardService: DashboardService) {
        this.dashboardService.getData().pipe(first()).subscribe(dashboard => {
            this.dashboard = dashboard;
        });
    }

    ngOnInit(): void {
        // generate random values for mainChart
        let currentUser = localStorage.getItem(config.currentUser);
        if (currentUser) {
            var mUser: User = JSON.parse(currentUser);
            if (mUser) {
                let role = mUser.role;
                if (role === 'ROLE_ADMIN') {
                    this.isAdmin = true;
                }
            }
        }
    }
}
