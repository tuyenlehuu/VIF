import { NgModule, ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { StockTrackingComponent } from './stock-tracking.component';
import { CEStockTrackingComponent } from './create-edit-stock-tracking.component';

const routes: Routes = [
  {
    path: '',
    // component: UserComponent,
    data: {
      title: 'Theo dõi cổ phiếu' 
    },
    children: [
      {
        path: '',
        redirectTo: 'stock-tracking-management'
      },
      {
        path: '',
        component: StockTrackingComponent,
        data: {
          title: ''
        }
      },
      {
        path: 'create-edit-stock-tracking/:id',
        component: CEStockTrackingComponent,
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
export class StockTrackingRouting {}
