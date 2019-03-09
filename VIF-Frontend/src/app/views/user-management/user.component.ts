import { Component, OnInit } from '@angular/core';
import { User } from '../../models/User.model';
import { UserService } from '../../services/user.service';
import { first } from 'rxjs/operators';

@Component({
  templateUrl: 'user.component.html'
})
export class UserComponent implements OnInit {
  users: User[] = [];
  myId = 1991;

  constructor(private userService:UserService){}

  ngOnInit(): void {
    this.userService.getAll().pipe(first()).subscribe(users=>{
      this.users = users;
    });
  }
}
