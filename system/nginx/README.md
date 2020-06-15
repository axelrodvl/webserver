# nginx configuration

- Put nginx.conf to `/etc/nginx/nginx.conf`
- Copy sites-available/tou to `/etc/nginx/sites-available`
- Enable config:
```
ln -s /etc/nginx/sites-available/tou /etc/nginx/sites-enabled/
```
