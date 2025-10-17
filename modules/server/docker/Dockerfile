FROM docker.1ms.run/ubuntu:latest
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo 'Asia/Shanghai' >/etc/timezone
ADD ./build/native/nativeCompile/server server
RUN apt update && apt install fontconfig -y && apt install --fix-broken -y
CMD ["./server"]