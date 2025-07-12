# 构建阶段
FROM node:18-alpine AS build
WORKDIR /app
COPY . .
RUN npm install && npm run build

# 生产环境
FROM nginx:alpine
COPY --from=build /app/dist /usr/share/nginx/html
EXPOSE 80
COPY bootstrap.sh .
RUN chmod +x bootstrap.sh
ENTRYPOINT ["./bootstrap.sh"]