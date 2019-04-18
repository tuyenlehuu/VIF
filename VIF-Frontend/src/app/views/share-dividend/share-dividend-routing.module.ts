import { NgModule, ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ShareDividendComponent } from './share-dividend.component';



const routes: Routes = [
  {
    path: '',
    component: ShareDividendComponent,
    data: {
      title: 'Chia cổ tức'
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ShareDividenRoutingModule { }
