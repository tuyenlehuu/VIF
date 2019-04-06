import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

// Import Containers
import { DefaultLayoutComponent } from './containers';

import { P403Component } from './views/error/403.component';
import { P404Component } from './views/error/404.component';
import { P500Component } from './views/error/500.component';
import { LoginComponent } from './views/login/login.component';
import { ResetPassComponent } from './views/forgot-pass/forgot.pass.component';
import { ChangePassComponent } from './views/change-pass/change.pass.component';
import { AuthGuard } from './guards/auth.guard';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'dashboard',
    pathMatch: 'full',
    canActivate: [AuthGuard],
  },
  {
    path: '404',
    component: P404Component,
    data: {
      title: 'Page 404'
    }
  },
  {
    path: '500',
    component: P500Component,
    data: {
      title: 'Page 500'
    }
  },
  {
    path: 'login',
    component: LoginComponent,
    data: {
      title: 'Login Page'
    }
  },
  {
    path: 'change-pass',
    component: ChangePassComponent,
    data: {
      title: 'Thay đổi mật khẩu'
    }
  },
  {
    path: 'forgot-pass',
    component: ResetPassComponent,
    data: {
      title: 'Cấp lại mật khẩu'
    }
  },
  {
    path: '',
    component: DefaultLayoutComponent,
    data: {
      title: 'Trang chủ'
    },
    canActivate: [AuthGuard],
    children: [
      {
        path: 'dashboard',
        loadChildren: './views/dashboard/dashboard.module#DashboardModule'
      },
      {
        path: 'invest-management',
        loadChildren: './views/invest-management/invest.module#InvestModule'
      },
      {
        path: 'managementttttt',
        loadChildren: './views/invest-management/invest.module#InvestModule'
      },
      {
        path: 'investor-transaction',
        loadChildren: './views/investor-transaction/investor.transaction.module#InvestorTransModule'
      },
      {
        path: 'user-management',
        loadChildren: './views/user-management/user.module#UserModule'
      },
      {
        path: 'cus-invest-history',
        loadChildren: './views/customer-invest-history/cus.invest.history.module#CusInvestHistoryModule'
      },
      {
        path: 'asset-management',
        loadChildren: './views/asset-management/asset.module#AssetModule'
      },
      {
        path: 'charts',
        loadChildren: './views/chartjs/chartjs.module#ChartJSModule'
      },
      {
        path: '403',
        component: P403Component
      }
    ]
  },
  { path: '**', component: P404Component }
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
