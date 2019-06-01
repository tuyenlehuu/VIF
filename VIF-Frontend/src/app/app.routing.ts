import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

// Import Containers
import { DefaultLayoutComponent } from './containers';

import { P403Component } from './views/error/403.component';
import { P404Component } from './views/error/404.component';
import { P500Component } from './views/error/500.component';
import { LoginComponent } from './views/login/login.component';
import { ResetPassComponent } from './views/forgot-pass/forgot.pass.component';
import { ChangeResetPassComponent } from './views/change-reset-pass/change.reset.pass.component';
import { ChangePasswordComponent } from './views/change-password/change.pass.component';
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
    path: 'change-reset-pass',
    component: ChangeResetPassComponent,
    data: {
      title: 'Thay đổi mật khẩu'
    }
  },
  {
    path: 'change-password',
    component: ChangePasswordComponent,
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
        path: 'invest-management/share-devidend',
        loadChildren: './views/share-dividend/share-dividend.module#ShareDividendModule'
      },
      {
        path: 'invest-appro',
        loadChildren: './views/invest-appro/investAppro.module#InvestApproModule'
      },
      {
        path: 'investor-transaction',
        loadChildren: './views/investor-transaction/investor.transaction.module#InvestorTransModule'
      },
      {
        path: 'c_commission',
        loadChildren: './views/c_commission/c_commission_module#CompanyCommissionModule'
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

        path: 'customer-management',

        loadChildren: './views/customer-management/customer.module#CustomerModule'
      },
      {
        path: 'app-param',
        loadChildren: './views/appParam/appParam.module#AppParamModule'
      },
      {
        path: "branch-managenment",
        loadChildren: './views/branch-managenment/branch.module#BranchModule'
      },
      {
        path: '403',
        component: P403Component

      },
      {
        path:"invest-request",
        loadChildren: './views/invest-request/invest-req.module#InvestRequestModule'
      }
    ]
  },
  { path: '**', component: P404Component }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
