import { NgModule, ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { OtherAssetComponent } from './other.asset.component';
import { CEOtherAssetComponent } from './create.edit.other.asset.component';

const routes: Routes = [
  {
    path: '',
    // component: UserComponent,
    data: {
      title: 'Quản lý tài sản khác' 
    },
    children: [
      {
        path: '',
        redirectTo: 'other-asset-management'
      },
      {
        path: '',
        component: OtherAssetComponent,
        data: {
          title: ''
        }
      },
      {
        path: 'ce-other-asset/:id',
        component: CEOtherAssetComponent,
        data: {
          title: ''
        }
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class OtherAssetRoutingModule {}
