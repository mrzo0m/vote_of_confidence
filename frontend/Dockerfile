FROM alpine/git as clone
WORKDIR /app
RUN git clone -b master https://github.com/mrzo0m/vote_of_confidence.git

FROM node:lts-alpine as build-stage
WORKDIR /app
COPY --from=clone /app/vote_of_confidence/frontend/vocui/package*.json ./
RUN npm install
COPY --from=clone /app/vote_of_confidence/frontend/vocui/. .
RUN npm run build


FROM nginx as production-stage
RUN mkdir /app
COPY --from=build-stage /app/dist /app
COPY --from=clone /app/vote_of_confidence/frontend/nginx.conf /etc/nginx/nginx.conf
EXPOSE 80
