FROM node:22.11.0

WORKDIR /app

COPY package*.json ./

COPY ./ ./

ENV MONGO_URI=mongodb://mongodb:27017/userdb
ENV PORT=3000
ENV PRODUCT_SERVICE_URL=http://localhost:5000
ENV ORDER_SERVICE_URL=http://localhost:9090

RUN npm install

EXPOSE 3000

CMD ["node" , "index.js"]