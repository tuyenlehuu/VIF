import { NgModule, ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AppParamComponent } from './appParam.component';
import { CEAppParamComponent } from './creat.edit.appParam.component';

const routes: Routes = [
  {
    path: '',
    // component: AppParamComponent,
    data: {
      title: 'Quản lí config'
    },
    children: [
      {
        path: '',
        redirectTo: 'app-param'
      },
      {
        path: 'app-param',
        component: AppParamComponent,
        data: {
          title: ''
        }
      },
      {
        path: 'ce-appParam/:id',
        component: CEAppParamComponent,
        data: {
          title: ''
        }
      },

    ]

  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AppParamRoutingModule { }