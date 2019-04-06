import { NgModule, ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AssetComponent } from './asset.component';
import { CEAssetComponent } from './create.edit.asset.component';

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
        redirectTo: 'asset-management'
      },
      {
        path: '',
        component: AssetComponent,
        data: {
          title: ''
        }
      },
      {
        path: 'create-edit-asset/:id',
        component: CEAssetComponent,
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
export class AssetRoutingModule {}
