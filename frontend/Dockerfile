FROM node:16.16.0 as builder

WORKDIR /app
COPY package.json .

RUN npm install --save --legacy-peer-deps
COPY . .
RUN npm run build

FROM nginx:alpine

COPY --from=builder /app/dist /usr/share/nginx/html
RUN rm /etc/nginx/conf.d/default.conf
COPY nginx/nginx.conf /etc/nginx/conf.d

EXPOSE 8083

CMD ["nginx", "-g", "daemon off;"]
