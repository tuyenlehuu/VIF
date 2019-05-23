import { Component, OnInit, TemplateRef } from '@angular/core';
import { User } from '../../models/User.model';
import {Customer} from '../../models/Customer.model';
import { CustomerService } from '../../services/customer.service';
import { first, catchError } from 'rxjs/operators';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { ToastrService } from 'ngx-toastr';
import { Pager } from '../../models/Pager';
import { Router, ActivatedRoute } from '@angular/router';



@Component({
   
    templateUrl: 'customer-users.component.html'
})
export class UserCustomerComponent implements OnInit {
    users: User[] = [];
    customer: Customer;
    id: number;
    customerService: CustomerService;
    p: number = 1;
    total: number;
    pageSize: number = 5;
 

    constructor(private route: ActivatedRoute,private router: Router, private customerSevice: CustomerService, private modalService: BsModalService, private toastrService: ToastrService) { 
  
      this.getPage(1);
    }

    ngOnInit(): void {
      
      
    }

    getPage(page: number) {
        this.id = this.route.snapshot.params['id'];
      
        var pager: Pager = new Pager();
        pager.page = page;
        pager.pageSize = this.pageSize;
        this.customerSevice.getUsers(this.id).pipe(first()).subscribe((respons: any) => {
            this.users = respons;
            this.total = respons.totalRow;
            this.p = page;
        });
    }


    

}