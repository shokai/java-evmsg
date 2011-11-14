#!/usr/bin/env ruby
require 'rubygems'
require 'socket'
require 'eventmachine'

class Receiver < EM::Connection
  def receive_data data
    puts data
    port, host = Socket.unpack_sockaddr_in get_peername
    puts "#{host}:#{port} - #{data}"
    send_datagram("echo> #{data}", host, port)
  end
end

EM::run do
  EM::open_datagram_socket('0.0.0.0', 5001, Receiver)
end

